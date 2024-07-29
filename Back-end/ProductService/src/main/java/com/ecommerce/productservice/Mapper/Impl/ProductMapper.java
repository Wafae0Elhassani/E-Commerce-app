package com.ecommerce.productservice.Mapper.Impl;

import com.ecommerce.productservice.Mapper.EntityMapper;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.CategoryRepository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductMapper implements EntityMapper<ProductDto, Product> {

    private final CategoryRepository categoryRepository;

    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        return product;
    }

    @Override
    public ProductDto toDto(Product entity) {
        ProductDto productDto = new ProductDto();
        productDto.setId(entity.getId());
        productDto.setName(entity.getName());
        productDto.setDescription(entity.getDescription());
        productDto.setPrice(entity.getPrice());
        productDto.setCreatedAt(entity.getCreatedAt());
        productDto.setUpdatedAt(entity.getUpdatedAt());
        if(entity.getCategory() != null){
            productDto.setCategoryId(entity.getCategory().getId());
        }
        return productDto;
    }

    @Override
    public List<Product> toEntity(List<ProductDto> dtoList) {
        List<Product> entityList = new ArrayList<>();
        for(ProductDto dto : dtoList){
            entityList.add(toEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<ProductDto> toDto(List<Product> entityList) {
        List<ProductDto> dtoList = new ArrayList<>();
        for(Product entity: entityList){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }
}
