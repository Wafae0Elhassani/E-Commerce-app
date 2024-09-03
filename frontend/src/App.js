import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import HomePage from './pages/HomePage';
import AddProduct from './pages/addProduct';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/add" element={<AddProduct />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
