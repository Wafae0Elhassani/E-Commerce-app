package com.ecommerce.productservice.service.product;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
}
