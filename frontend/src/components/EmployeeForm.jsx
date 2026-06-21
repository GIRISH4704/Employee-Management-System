import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api/axios';

const EMPTY = {
  firstName: '', lastName: '', email: '',
  departmentId: '', designation: '', salary: '', joiningDate: '',
};

function EmployeeForm() {
  // useParams() reads the :id from /edit/:id — undefined on the Add page
  const { id } = useParams();
  const isEdit  = Boolean(id);
  const navigate = useNavigate();

  const [form, setForm]             = useState(EMPTY);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading]       = useState(false);
  const [error, setError]           = useState(null);

  useEffect(() => {
    // Load department dropdown
    api.get('/api/departments').then(res => setDepartments(res.data));

    // Pre-fill form when editing
    if (isEdit) {
      api.get(`/api/employees/${id}`).then(res => {
        const e = res.data;
        setForm({
          firstName:    e.firstName,
          lastName:     e.lastName,
          email:        e.email,
          departmentId: e.departmentId,
          designation:  e.designation,
          salary:       e.salary,
          joiningDate:  e.joiningDate,
        });
      });
    }
  }, [id, isEdit]);

  const handleChange = e => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const payload = {
        ...form,
        departmentId: Number(form.departmentId),
        salary:       Number(form.salary),
      };
      if (isEdit) {
        await api.put(`/api/employees/${id}`, payload);
      } else {
        await api.post('/api/employees', payload);
      }
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save. Check all fields.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <h2>{isEdit ? 'Edit Employee' : 'Add New Employee'}</h2>
      {error && <p className="error">{error}</p>}

      <form onSubmit={handleSubmit} className="emp-form">
        <div className="form-row">
          <label>First Name</label>
          <input name="firstName" value={form.firstName}
            onChange={handleChange} required />
        </div>
        <div className="form-row">
          <label>Last Name</label>
          <input name="lastName" value={form.lastName}
            onChange={handleChange} required />
        </div>
        <div className="form-row">
          <label>Email</label>
          <input name="email" type="email" value={form.email}
            onChange={handleChange} required />
        </div>
        <div className="form-row">
          <label>Department</label>
          <select name="departmentId" value={form.departmentId}
            onChange={handleChange} required>
            <option value="">Select department</option>
            {departments.map(d => (
              <option key={d.id} value={d.id}>{d.name}</option>
            ))}
          </select>
        </div>
        <div className="form-row">
          <label>Designation</label>
          <input name="designation" value={form.designation}
            onChange={handleChange} required />
        </div>
        <div className="form-row">
          <label>Salary (₹)</label>
          <input name="salary" type="number" min="1" value={form.salary}
            onChange={handleChange} required />
        </div>
        <div className="form-row">
          <label>Joining Date</label>
          <input name="joiningDate" type="date" value={form.joiningDate}
            onChange={handleChange} required />
        </div>

        <div className="form-actions">
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Saving...' : isEdit ? 'Update Employee' : 'Add Employee'}
          </button>
          <button type="button" className="btn-secondary"
            onClick={() => navigate('/')}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default EmployeeForm;
