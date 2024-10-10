package uz.developers.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.messenger.payload.CategoryDto;
import uz.developers.messenger.payload.CustomApiResponse;
import uz.developers.messenger.payload.UserDto;
import uz.developers.messenger.service.CategoryService;
import uz.developers.messenger.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to Category operations.
 * This controller provides RESTful endpoints to manage category records,
 * including creating, updating, retrieving, and deleting category information.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;

    /**
     * Constructor for CategoryController.
     *
     * @param categoryService the service to manage user records
     * @Autowired automatically injects the CategoryService bean
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    /**
     * Retrieve a list of all categories.
     *
     * This method fetches all user records and returns them as a list of CategoryDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of CategoryDto representing all categories
     */
    @Operation(summary = "Get all Categories", description = "Retrieve a list of all categories.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of categories.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getCategories();
        CustomApiResponse<List<CategoryDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of categories.",
                true,
                categoryDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    /**
     * Retrieve a category by their unique ID using the provided CategoryDto.
     *
     * This method retrieves a category's details based on their ID and returns
     * a CustomApiResponse containing the corresponding CategoryDto if found.
     * If the category does not exist, it returns a CustomApiResponse with a
     * message indicating that the user was not found and a 404 Not Found status.
     *
     * @param id the ID of the category to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CategoryDto and
     *         an HTTP status of OK, or a NOT FOUND status if the category does not exist.
     */
    @Operation(summary = "Get Category by ID", description = "Retrieve a category by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category.")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDto> categoryDto = categoryService.getCategoryById(id);
        if (categoryDto.isPresent()){
            CustomApiResponse<CategoryDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the category.",
                    true,
                    categoryDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CategoryDto> response = new CustomApiResponse<>(
                    "Category not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new category.
     *
     * This method validates the incoming category data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param categoryDto the DTO containing the category information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved category data
     */
    @Operation(summary = "Create a new Category", description = "Create a new category record.")
    @ApiResponse(responseCode = "201", description = "Category created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        CustomApiResponse<CategoryDto> response = new CustomApiResponse<>(
                "Category created successfully",
                true,
                savedCategory
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    /**
     * Update the details of an existing category using the provided CategoryDto.
     *
     * This method accepts the category's ID and a DTO containing updated category details.
     * It updates the category record if it exists and returns the updated CategoryDto object.
     *
     * @param id the ID of the category to be updated
     * @param categoryDto the DTO containing updated category details
     * @return a ResponseEntity containing a CustomApiResponse with the updated CategoryDto,
     *         or a NOT FOUND response if the category does not exist
     */
    @Operation(summary = "Update category", description = "Update the details of an existing user.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CategoryDto>>  updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        Optional<CategoryDto> categoryDtoOptional = categoryService.getCategoryById(id);
        if (categoryDtoOptional.isPresent()) {
            CategoryDto updateCategory = categoryService.updateCategory(id, categoryDto);
            CustomApiResponse<CategoryDto> response = new CustomApiResponse<>(
                    "Category updated successfully",
                    true,
                    updateCategory
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CategoryDto> response = new CustomApiResponse<>(
                    "Category not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Delete a category by their ID.
     *
     * This method deletes the category record based on the given ID if it exists.
     *
     * @param id the ID of the category to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the category does not exist
     */
    @Operation(summary = "Delete Category", description = "Delete a category by its ID.")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        Optional<CategoryDto> categoryDto = categoryService.getCategoryById(id);
        if (categoryDto.isPresent()) {
            categoryService.deleteCategory(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Category deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Category not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }




}
