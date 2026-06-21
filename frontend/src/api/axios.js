import axios from 'axios';

// All API calls go to the Spring Boot backend on port 8080.
// We hardcode admin credentials here since we don't have a login page yet.
// In a real app, you'd store a JWT token from a login endpoint instead.
const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
    // btoa() converts "admin:admin123" to its Base64 form for HTTP Basic Auth
    'Authorization': 'Basic ' + btoa('admin:admin123'),
  },
});

export default api;
