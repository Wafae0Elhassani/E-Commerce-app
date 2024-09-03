import useFetch from "../components/useFetch";

const HomePage = () => {
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

    return (
        <div className="homepage-container">
            <h1>Product List</h1>
            {loading && <p>Loading...</p>}
            {error && <p className="error-message">Error: {error}</p>}
            <div className="product-grid">
                {products && products.map(product => (
                    <div key={product.id} className="product-card">
                        <h2>{product.name}</h2>
                        <p>{product.description}</p>
                        <p><strong>Price:</strong> {product.price.toFixed(2)} MAD</p>
                        <p><strong>Created At:</strong> {new Date(product.createdAt).toLocaleDateString()}</p>
                        <p><strong>Updated At:</strong> {new Date(product.updatedAt).toLocaleDateString()}</p>
                        <p><strong>Category:</strong> {getCategoryDisplayName(product.category)}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default HomePage;
