package com.ecommerce.productservice.Mapper.Impl;

import com.ecommerce.productservice.Mapper.EntityMapper;
import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper implements EntityMapper<CategoryDto, Category> {

    private final ProductRepository productRepository;

    public CategoryMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        List<Product> products= new ArrayList<>();
        if(dto.getProductIds() != null){
            for(Long id: dto.getProductIds()){
                Product product = productRepository.findById(id).orElse(null);
                products.add(product);
            }
        }
        category.setProducts(products);
        return category;
    }

    @Override
    public CategoryDto toDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        categoryDto.setDescription(entity.getDescription());
        categoryDto.setCreatedAt(entity.getCreatedAt());
        categoryDto.setUpdatedAt(entity.getUpdatedAt());
        List<Long> longList = new ArrayList<>();
        if(entity.getProducts() != null){
            for(Product product: entity.getProducts()){
                longList.add(product.getId());
            }
        }
        categoryDto.setProductIds(longList);
        return categoryDto;
    }

    @Override
    public List<Category> toEntity(List<CategoryDto> dtoList) {
        List<Category> entityList = new ArrayList<>();
        for(CategoryDto dto: dtoList){
            entityList.add(toEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<CategoryDto> toDto(List<Category> entityList) {
        List<CategoryDto> dtoList = new ArrayList<>();
        for(Category entity: entityList){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }
}
