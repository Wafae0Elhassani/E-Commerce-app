package com.ecommerce.productservice.service.product.Impl;

import com.ecommerce.productservice.Mapper.Impl.ProductMapper;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.product.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            LocalDateTime createdAt = product.getCreatedAt();
            BeanUtils.copyProperties(productMapper.toEntity(productDto),product, "id","createdAt");
            product.setId(id);
            product.setCreatedAt(createdAt);
            product.setUpdatedAt(LocalDateTime.now());
            return productMapper.toDto(productRepository.save(product));
        }

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return productMapper.toDto(Objects.requireNonNull(productRepository.findById(id).orElse(null)));
    }
}
