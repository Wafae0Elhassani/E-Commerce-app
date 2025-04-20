import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

const ProductDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/v1/product/getProduct/${id}`)
            .then(res => res.json())
            .then(data => setProduct(data))
            .catch(err => console.error("Failed to fetch product", err));
    }, [id]);

    const getCategoryDisplayName = (category) => {
        switch (category) {
            case "ELECTRONICS": return "Electronics";
            case "CLOTHING_MEN": return "Men's Clothing";
            case "CLOTHING_WOMEN": return "Women's Clothing";
            case "BOOKS": return "Books";
            case "HOME": return "Home";
            case "JEWELRY": return "Jewelry";
            case "OTHERS": return "Others";
            default: return "";
        }
    };

    const handleDelete = async () => {
        const confirmDelete = window.confirm("Are you sure you want to delete this product?");
        if (!confirmDelete) return;

        const res = await fetch(`http://localhost:8080/api/v1/product/delete/${id}`, {
            method: "DELETE"
        });

        if (res.ok) {
            alert("Product deleted");
            navigate('/home');
        } else {
            alert("Failed to delete product");
        }
    };

    const handleUpdate = () => {
        navigate(`/products/update/${id}`);
    };

    if (!product) return <p>Loading product...</p>;

    return (
        <div className="product-details-page">
            <div className="product-details-card">
                <h2>{product.name}</h2>
                <p><strong>Description:</strong> {product.description}</p>
                <p><strong>Price:</strong> {product.price.toFixed(2)} MAD</p>
                <p><strong>Category:</strong> {getCategoryDisplayName(product.category)}</p>
                <p><strong>Created At:</strong> {new Date(product.createdAt).toLocaleDateString()}</p>
                <p><strong>Updated At:</strong> {new Date(product.updatedAt).toLocaleDateString()}</p>

                <div className="details-button-group">
                    <button className="details-update-btn" onClick={handleUpdate}>Update</button>
                    <button className="details-delete-btn" onClick={handleDelete}>Delete</button>
                    <button className="details-back-btn" onClick={() => navigate('/home')}>Back to List</button>
                </div>
            </div>
        </div>
    );
};

export default ProductDetails;