package com.altimetrik.category.services;

import com.altimetrik.category.dto.CategoryAPIResponse;
import com.altimetrik.category.dto.CategoryDto;
import com.altimetrik.category.dto.CategoryResponse;
import com.altimetrik.category.errorHandling.ErrorHandlingControllerAdvice;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryDto categoryDto) throws IOException, ErrorHandlingControllerAdvice;

    CategoryResponse getCategoryDetails(String categoryType, int page, int size, String sortByOrder, String sortBy) throws ErrorHandlingControllerAdvice;

    CategoryResponse deleteCategory(CategoryDto categoryDto);
}
