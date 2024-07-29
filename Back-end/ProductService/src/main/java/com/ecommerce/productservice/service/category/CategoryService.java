package com.ecommerce.productservice.service.category;


import com.ecommerce.productservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);

    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);

}
