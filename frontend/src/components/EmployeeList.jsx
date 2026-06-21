import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';

function EmployeeList() {
  const [employees, setEmployees]     = useState([]);
  const [departments, setDepartments] = useState([]);
  const [selectedDept, setSelectedDept] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState(null);
  const navigate = useNavigate();

  // Fetch departments once for the filter dropdown
  useEffect(() => {
    api.get('/api/departments')
      .then(res => setDepartments(res.data))
      .catch(() => {});
  }, []);

  const fetchEmployees = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const url = selectedDept
        ? `/api/employees/department/${selectedDept}`
        : '/api/employees';
      const res = await api.get(url);
      setEmployees(res.data);
    } catch {
      setError('Could not load employees. Is the backend running?');
    } finally {
      setLoading(false);
    }
  }, [selectedDept]);

  // Re-fetch whenever the department filter changes
  useEffect(() => { fetchEmployees(); }, [fetchEmployees]);

  const handleDelete = async (id, name) => {
    if (!window.confirm(`Delete ${name}? This cannot be undone.`)) return;
    try {
      await api.delete(`/api/employees/${id}`);
      fetchEmployees();
    } catch {
      alert('Failed to delete employee.');
    }
  };

  return (
    <div>
      <div className="toolbar">
        <h2>Employees</h2>
        <select
          className="dept-filter"
          value={selectedDept}
          onChange={e => setSelectedDept(e.target.value)}
        >
          <option value="">All Departments</option>
          {departments.map(d => (
            <option key={d.id} value={d.name}>{d.name}</option>
          ))}
        </select>
      </div>

      {loading && <p className="status">Loading...</p>}
      {error   && <p className="error">{error}</p>}

      {!loading && !error && employees.length === 0 && (
        <p className="status">No employees found. Add one to get started.</p>
      )}

      {!loading && !error && employees.length > 0 && (
        <table className="emp-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Department</th>
              <th>Designation</th>
              <th>Salary</th>
              <th>Joining Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.map(emp => (
              <tr key={emp.id}>
                <td>{emp.firstName} {emp.lastName}</td>
                <td>{emp.email}</td>
                <td><span className="dept-badge">{emp.departmentName}</span></td>
                <td>{emp.designation}</td>
                <td>₹{emp.salary.toLocaleString('en-IN')}</td>
                <td>{emp.joiningDate}</td>
                <td className="actions">
                  <button className="btn-edit"
                    onClick={() => navigate(`/edit/${emp.id}`)}>
                    Edit
                  </button>
                  <button className="btn-delete"
                    onClick={() => handleDelete(emp.id, `${emp.firstName} ${emp.lastName}`)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default EmployeeList;
