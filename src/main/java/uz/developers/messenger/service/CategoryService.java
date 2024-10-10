package uz.developers.messenger.service;

import uz.developers.messenger.exceptions.CategoryException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    //get all
    List<CategoryDto> getCategories();

    //get by ID
    Optional<CategoryDto> getCategoryById(Long id) throws ResourceNotFoundException;

    //create
    CategoryDto createCategory(CategoryDto categoryDto) throws CategoryException;

    //update
    CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws ResourceNotFoundException;

    //delete
    void deleteCategory(Long id) throws ResourceNotFoundException;













}
