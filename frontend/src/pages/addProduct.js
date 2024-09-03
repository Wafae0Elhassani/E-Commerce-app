import { useState } from "react";
import categoryEnum from "../enums/CategoryEnum";
import { useNavigate } from "react-router-dom";

const CreateProduct = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [category, setCategory] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        let categoryEnum;
        switch (category) {
            case "Women's Clothing":
                categoryEnum = "CLOTHING_WOMEN";
                break;
            case "Men's Clothing":
                categoryEnum = "CLOTHING_MEN";
                break;
            default:
                categoryEnum = category.toUpperCase();
                break;
        }

        const product = { name, description, price, category: categoryEnum };
        fetch('http://localhost:8080/api/v1/product/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        })
        navigate('/');
    };

    return (
        <div className="add-product">
            <h1>Add New Product</h1>
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
                    {Object.values(categoryEnum).map((cat) => (
                        <option key={cat} value={cat}>
                            {cat}
                        </option>
                    ))}
                </select>
                <button type="submit">Add Product</button>
            </form>
        </div>
    );
};

export default CreateProduct;
