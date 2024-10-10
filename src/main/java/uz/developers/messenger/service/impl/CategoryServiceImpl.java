package uz.developers.messenger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.messenger.entity.Category;
import uz.developers.messenger.exceptions.CategoryException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.CategoryDto;
import uz.developers.messenger.repository.CategoryRepository;
import uz.developers.messenger.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final ModelMapper modelMapper;


    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::categoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));

        // Convert Category entity to CategoryDto
        CategoryDto categoryDto = categoryToDto(category);
        return Optional.ofNullable(categoryDto);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // 1. Convert DTO to entity
        Category category = dtoToCategory(categoryDto);

        // 2. Perform business checks on the entity
        if (category.getTitle() == null) {
            throw new CategoryException("Category title name must not be null");
        }

        // 3. Checking that the title column does not exist
        boolean exists = categoryRepository.existsByTitle(category.getTitle());
        if (exists) {
            throw new CategoryException("Category with this title name already exists");
        }

        // 4. Save Employee
        Category savedCategory = categoryRepository.save(category);

        // 5. Convert the saved Category to DTO and return
        return categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));

        // Use ModelMapper to map DTO to entity
        Category categoryDetails = dtoToCategory(categoryDto);

        // update category details
        existingCategory.setTitle(categoryDetails.getTitle());
        existingCategory.setDescription(categoryDetails.getDescription());

        // Save updated category
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Convert updated category entity to DTO and return
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
        categoryRepository.delete(category);
    }


    // DTO ---> Entity
    private Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    // Entity ---> DTO
    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

}
