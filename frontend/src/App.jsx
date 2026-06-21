import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom';
import EmployeeList from './components/EmployeeList';
import EmployeeForm from './components/EmployeeForm';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <nav className="navbar">
        <span className="navbar-brand">Employee Management System</span>
        <div className="nav-links">
          <NavLink to="/" end className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            Employees
          </NavLink>
          <NavLink to="/add" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            + Add Employee
          </NavLink>
        </div>
      </nav>

      <main className="container">
        <Routes>
          <Route path="/"         element={<EmployeeList />} />
          <Route path="/add"      element={<EmployeeForm />} />
          <Route path="/edit/:id" element={<EmployeeForm />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
