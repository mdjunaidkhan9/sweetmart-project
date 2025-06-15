import { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/admincategory.css'; // using your shared CSS

function AdminCategory() {
  const token = localStorage.getItem('token');
  const [view, setView] = useState('all');
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState({
    categoryName: '',
    categoryDescription: ''
  });
  const [updateId, setUpdateId] = useState('');

  const headers = { Authorization: `Bearer ${token}` };

  const fetchCategories = async () => {
    try {
      const res = await axios.get('http://localhost:8080/category/admin/all', { headers });
      setCategories(res.data);
    } catch {
      toast.error('Failed to load categories');
    }
  };

  const fetchCategoriesWithProducts = async () => {
    try {
      const res = await axios.get('http://localhost:8080/category/all-with-products', { headers });
      setCategories(res.data);
    } catch {
      toast.error('Failed to load categories with products');
    }
  };

  const handleAdd = async () => {
    try {
      await axios.post('http://localhost:8080/category/admin/add', form, { headers });
      toast.success('Category added');
      setForm({ categoryName: '', categoryDescription: '' });
      fetchCategories();
    } catch {
      toast.error('Add failed');
    }
  };

  const handleUpdate = async () => {
    if (!updateId) {
      toast.error('ID required');
      return;
    }
    try {
      await axios.put(`http://localhost:8080/category/admin/update/${updateId}`, form, { headers });
      toast.success('Category updated');
      setUpdateId('');
      setForm({ categoryName: '', categoryDescription: '' });
      fetchCategories();
    } catch {
      toast.error('Update failed');
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/category/admin/delete/${id}`, { headers });
      toast.success('Category deleted');
      fetchCategories();
    } catch {
      toast.error('Delete failed');
    }
  };

  useEffect(() => {
    if (view === 'all') fetchCategories();
    else if (view === 'withProducts') fetchCategoriesWithProducts();
  }, [view]);

  return (
    <div className="user-products-container">
      <div className="user-products-sidebar">
        <h3>Category Admin</h3>
        <button onClick={() => setView('add')}>Add Category</button>
        <button onClick={() => setView('all')}>View All</button>
        <button onClick={() => setView('withProducts')}>With Products</button>
        <button onClick={() => setView('update')}>Update</button>
        <button onClick={() => setView('delete')}>Delete</button>
      </div>

      <div className="user-products-content">
        <ToastContainer />

        {view === 'add' && (
          <div className="user-products-form">
            <h2>Add Category</h2>
            <input
              placeholder="Category Name"
              value={form.categoryName}
              onChange={(e) => setForm({ ...form, categoryName: e.target.value })}
            />
            <input
              placeholder="Category Description"
              value={form.categoryDescription}
              onChange={(e) => setForm({ ...form, categoryDescription: e.target.value })}
            />
            <button onClick={handleAdd}>Submit</button>
          </div>
        )}

        {view === 'update' && (
          <div className="user-products-form">
            <h2>Update Category</h2>
            <input
              placeholder="Category ID"
              value={updateId}
              onChange={(e) => setUpdateId(e.target.value)}
            />
            <input
              placeholder="Category Name"
              value={form.categoryName}
              onChange={(e) => setForm({ ...form, categoryName: e.target.value })}
            />
            <input
              placeholder="Category Description"
              value={form.categoryDescription}
              onChange={(e) => setForm({ ...form, categoryDescription: e.target.value })}
            />
            <button onClick={handleUpdate}>Update</button>
          </div>
        )}

        {(view === 'all' || view === 'delete') && (
          <>
            <h2>Categories</h2>
            <div className="user-products-grid">
              {categories.map((cat) => (
                <div className="user-product-card" key={cat.categoryId}>
                  <h3>{cat.categoryName}</h3>
                  <p>{cat.categoryDescription}</p>
                  {view === 'delete' && (
                    <button onClick={() => handleDelete(cat.categoryId)}>Delete</button>
                  )}
                </div>
              ))}
            </div>
          </>
        )}

        {view === 'withProducts' && (
          <>
            <h2>Categories with Products</h2>
            <div className="user-products-grid">
              {categories.map((cat) => (
                <div className="user-product-card" key={cat.categoryId}>
                  <h3>{cat.categoryName}</h3>
                  <p>{cat.categoryDescription}</p>
                  <strong>Products:</strong>
                  <ul>
                    {cat.products?.map((p) => (
                      <li key={p.productId}>{p.productName}</li>
                    ))}
                  </ul>
                </div>
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default AdminCategory;
