import { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/userproducts.css';

function UserProducts() {
  const token = localStorage.getItem('token');
  const [view, setView] = useState('all');
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({
    productName: '',
    productDescription: '',
    productPrice: 0,
    productCategory: '',
    productAvailable: true
  });
  const [updateId, setUpdateId] = useState('');

  const headers = { Authorization: `Bearer ${token}` };

  const fetchProducts = async (type) => {
    let url = 'http://localhost:8080/products/allproduct';
    if (type === 'price') url += '/sortbyprice';
    if (type === 'name') url += '/sortbyname';

    try {
      const res = await axios.get(url, { headers });
      setProducts(res.data);
    } catch {
      toast.error("Failed to fetch products");
    }
  };

  const handleAdd = async () => {
    try {
      await axios.post('http://localhost:8080/products/admin/addproduct', form, { headers });
      toast.success('Product added');
      setForm({
        productName: '',
        productDescription: '',
        productPrice: 0,
        productCategory: '',
        productAvailable: true
      });
      fetchProducts(view);
    } catch {
      toast.error('Add failed');
    }
  };

  const handleUpdate = async () => {
    if (!updateId) {
      toast.error("Product ID required");
      return;
    }
    try {
      await axios.put(`http://localhost:8080/products/admin/update/${updateId}`, form, { headers });
      toast.success('Product updated');
      setUpdateId('');
      fetchProducts(view);
    } catch {
      toast.error('Update failed');
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/products/admin/delete/${id}`, { headers });
      toast.success('Product deleted');
      fetchProducts(view);
    } catch {
      toast.error('Delete failed');
    }
  };

  useEffect(() => {
    fetchProducts(view === 'all' ? '' : view);
  }, [view]);

  return (
    <div className="user-products-container">
      <div className="user-products-sidebar">
        <h3>Dashboard</h3>
        <button onClick={() => setView('add')}>Add Product</button>
        <button onClick={() => setView('all')}>View All</button>
        <button onClick={() => setView('price')}>Sort by Price</button>
        <button onClick={() => setView('name')}>Sort by Name</button>
        <button onClick={() => setView('update')}>Update Product</button>
        <button onClick={() => setView('delete')}>Delete Product</button>
      </div>

      <div className="user-products-content">
        <ToastContainer />
        {view === 'add' && (
          <div className="user-products-form">
            <h2>Add Product</h2>
            <input placeholder="Name" value={form.productName} onChange={e => setForm({ ...form, productName: e.target.value })} />
            <input placeholder="Description" value={form.productDescription} onChange={e => setForm({ ...form, productDescription: e.target.value })} />
            <input type="number" placeholder="Price" value={form.productPrice} onChange={e => setForm({ ...form, productPrice: e.target.value })} />
            <input placeholder="Category" value={form.productCategory} onChange={e => setForm({ ...form, productCategory: e.target.value })} />
            <label className="checkbox-label">
              <input type="checkbox" checked={form.productAvailable} onChange={e => setForm({ ...form, productAvailable: e.target.checked })} />
              Available
            </label>
            <button onClick={handleAdd}>Submit</button>
          </div>
        )}

        {(view === 'all' || view === 'price' || view === 'name') && (
          <>
            <h2>Products</h2>
            <div className="user-products-grid">
              {products.map((p) => (
                <div className="user-product-card" key={p.productId}>
                  <p><strong>ID:</strong> {p.productId}</p>
                  <h3>{p.productName}</h3>
                  <p>{p.productDescription}</p>
                  <p><strong>Price:</strong> ₹{p.productPrice}</p>
                  <p><strong>Category:</strong> {p.productCategory}</p>
                  <p><strong>Available:</strong> {p.productAvailable ? "Yes" : "No"}</p>
                </div>
              ))}
            </div>
          </>
        )}

        {view === 'update' && (
          <div className="user-products-form">
            <h2>Update Product</h2>
            <input placeholder="Product ID" value={updateId} onChange={e => setUpdateId(e.target.value)} />
            <input placeholder="Name" value={form.productName} onChange={e => setForm({ ...form, productName: e.target.value })} />
            <input placeholder="Description" value={form.productDescription} onChange={e => setForm({ ...form, productDescription: e.target.value })} />
            <input type="number" placeholder="Price" value={form.productPrice} onChange={e => setForm({ ...form, productPrice: e.target.value })} />
            <input placeholder="Category" value={form.productCategory} onChange={e => setForm({ ...form, productCategory: e.target.value })} />
            <label className="checkbox-label">
              <input type="checkbox" checked={form.productAvailable} onChange={e => setForm({ ...form, productAvailable: e.target.checked })} />
              Available
            </label>
            <button onClick={handleUpdate}>Update</button>
          </div>
        )}

        {view === 'delete' && (
          <div className="user-products-grid">
            {products.map((p) => (
              <div className="user-product-card" key={p.productId}>
                <p><strong>ID:</strong> {p.productId}</p>
                <h3>{p.productName}</h3>
                <p>{p.productDescription}</p>
                <p><strong>Price:</strong> ₹{p.productPrice}</p>
                <p><strong>Category:</strong> {p.productCategory}</p>
                <p><strong>Available:</strong> {p.productAvailable ? "Yes" : "No"}</p>
                <button onClick={() => handleDelete(p.productId)}>Delete</button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default UserProducts;
