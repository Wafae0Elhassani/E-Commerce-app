import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

const UpdateProduct = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [category, setCategory] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProductDetails = async () => {
            try {
                const res = await fetch(`http://localhost:8080/api/v1/product/getProduct/${id}`);
                if (!res.ok) {
                    const message = `An error occurred: ${res.status}`;
                    throw new Error(message);
                }
                const productData = await res.json();
                setName(productData.name);
                setDescription(productData.description);
                setPrice(productData.price);
                setCategory(getCategoryDisplayName(productData.category));
                setLoading(false);
            } catch (err) {
                setError(err.message);
                setLoading(false);
            }
        };

        fetchProductDetails();
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

    const getCategoryEnumValue = (displayName) => {
        switch (displayName) {
            case "Electronics": return "ELECTRONICS";
            case "Men's Clothing": return "CLOTHING_MEN";
            case "Women's Clothing": return "CLOTHING_WOMEN";
            case "Books": return "BOOKS";
            case "Home": return "HOME";
            case "Jewelry": return "JEWELRY";
            case "Others": return "OTHERS";
            default: return "";
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const updatedProduct = {
            name,
            description,
            price,
            category: getCategoryEnumValue(category)
        };

        try {
            const res = await fetch(`http://localhost:8080/api/v1/product/update/${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedProduct)
            });

            if (res.ok) {
                navigate(`/product/${id}`);
            } else {
                const errorData = await res.json();
                alert("Error updating product: " + errorData.message);
            }
        } catch (error) {
            alert("Network error: " + error.message);
        }
    };

    if (loading) return <p>Loading product details...</p>;
    if (error) return <p>Error loading product details: {error}</p>;

    return (
        <div className="add-product">
            <h1>Update Product</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="name">Product name: </label>
                <input
                    type="text"
                    id="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <label htmlFor="description">Product description: </label>
                <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                ></textarea>
                <label htmlFor="price">Product price: </label>
                <input
                    type="text"
                    id="price"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                />
                <label htmlFor="category">Category: </label>
                <select
                    id="category"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                >
                    <option value="">Select Category</option>
                    <option value="Electronics">Electronics</option>
                    <option value="Men's Clothing">Men's Clothing</option>
                    <option value="Women's Clothing">Women's Clothing</option>
                    <option value="Books">Books</option>
                    <option value="Home">Home</option>
                    <option value="Jewelry">Jewelry</option>
                    <option value="Others">Others</option>
                </select>
                <button type="submit">Update Product</button>
                <button type="button" onClick={() => navigate(`/product/${id}`)}>Cancel</button>
            </form>
        </div>
    );
};

export default UpdateProduct;