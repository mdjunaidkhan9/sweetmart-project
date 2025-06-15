import { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/users.css';

function Users() {
  const [users, setUsers] = useState([]);
  const [roleFilter, setRoleFilter] = useState('ALL');

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        let url = 'http://localhost:8080/auth/users';
        if (roleFilter !== 'ALL') {
          url = `http://localhost:8080/auth/users/role/${roleFilter}`;
        }
        const response = await axios.get(url);
        setUsers(response.data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, [roleFilter]);

  return (
    <div className="users-page">
      <h1 className="users-heading">User Management</h1>

      {/* Filter Buttons */}
      <div className="role-filter">
        {['ALL', 'USER', 'ADMIN'].map((role) => (
          <button
            key={role}
            className={`filter-btn ${roleFilter === role ? 'active' : ''}`}
            onClick={() => setRoleFilter(role)}
          >
            {role}
          </button>
        ))}
      </div>

      {/* User Cards */}
      <div className="users-grid">
        {users.length === 0 ? (
          <p className="no-users">No users found.</p>
        ) : (
          users.map((user) => (
            <div key={user.id} className="user-card">
              <h2 className="user-name">{user.username}</h2>
              <p className="user-email">{user.userEmail}</p>
              <span className={`user-role ${user.role.toLowerCase()}`}>
                {user.role}
              </span>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default Users;
