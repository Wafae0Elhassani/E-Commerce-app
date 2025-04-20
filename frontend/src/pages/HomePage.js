import useFetch from "../components/useFetch";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const HomePage = () => {
    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState("");
    const { data: products, error, loading } = useFetch('http://localhost:8080/api/v1/product/allProducts');

    const getCategoryDisplayName = (category) => {
        switch (category) {
            case "ELECTRONICS":
                return "Electronics";
            case "CLOTHING_MEN":
                return "Men's Clothing";
            case "CLOTHING_WOMEN":
                return "Women's Clothing";
            case "BOOKS":
                return "Books";
            case "HOME":
                return "Home";
            case "JEWELRY":
                return "Jewelry";
            case "OTHERS":
                return "Others";
            default:
                return "";
        }
    };

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const filteredProducts = products ? products.filter(product =>
        product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        product.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
        getCategoryDisplayName(product.category).toLowerCase().includes(searchTerm.toLowerCase())
    ) : [];

    return (
        <div className="homepage-container">
            <nav className="navbar">
                <div className="navbar-left">
                    <h1>Our Products</h1>
                </div>
                <div className="navbar-center">
                    <input
                        type="text"
                        placeholder="Search products..."
                        value={searchTerm}
                        onChange={handleSearchChange}
                        className="search-input"
                    />
                </div>
                <div className="navbar-right">
                    <button onClick={() => navigate('/add')} className="add-button">
                        Add New Product
                    </button>
                </div>
            </nav>

            <div className="content">
                {loading && <p>Loading products...</p>}
                {error && <p className="error-message">Error: {error}</p>}
                <div className="product-grid">
                    {filteredProducts.map(product => (
                        <div key={product.id} className="product-card" onClick={() => navigate(`/product/${product.id}`)}>
                            <h2>{product.name}</h2>
                            <p className="description">{product.description}</p>
                            <div className="product-details">
                                <p><strong>Price:</strong> {product.price.toFixed(2)} MAD</p>
                                <p><strong>Category:</strong> {getCategoryDisplayName(product.category)}</p>
                            </div>
                            <div className="timestamp">
                                <p className="created-at">Created: {new Date(product.createdAt).toLocaleDateString()}</p>
                                <p className="updated-at">Updated: {new Date(product.updatedAt).toLocaleDateString()}</p>
                            </div>
                        </div>
                    ))}
                </div>
                {filteredProducts.length === 0 && products && !loading && !error && (
                    <p className="no-results">No products found matching your search.</p>
                )}
            </div>
        </div>
    );
}

export default HomePage;