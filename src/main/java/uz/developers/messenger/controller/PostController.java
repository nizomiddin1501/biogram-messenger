package uz.developers.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.messenger.payload.CustomApiResponse;
import uz.developers.messenger.payload.PostDto;

import uz.developers.messenger.service.PostService;


import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to Post operations.
 * This controller provides RESTful endpoints to manage post records,
 * including creating, updating, retrieving, and deleting post information.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    /**
     * Constructor for PostController.
     *
     * @param postService the service to manage post records
     * @Autowired automatically injects the PostService bean
     */
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    ///
    //qushimcha methodlar post uchun

    /**
     * Retrieves a list of all posts for a specific category.
     *
     * This method fetches all posts that belong to a specific category
     * identified by the provided category ID. If no posts are found,
     * a ResourceNotFoundException is thrown.
     *
     * @param categoryId the ID of the category for which posts are to be retrieved
     * @return a ResponseEntity containing a CustomApiResponse with the list of PostDto
     * representing all posts for the specified category
     */
    @Operation(summary = "Get all Posts by Category", description = "Retrieve a list of all posts for a specific category.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts for the specified category.")
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CustomApiResponse<List<PostDto>>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        CustomApiResponse<List<PostDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of posts for category ID: " + categoryId,
                true,
                postDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    /**
     * Retrieves a list of all posts for a specific user.
     *
     * This method fetches all posts that belong to a specific user
     * identified by the provided user ID. If no posts are found,
     * a ResourceNotFoundException is thrown.
     *
     * @param userId the ID of the user for which posts are to be retrieved
     * @return a ResponseEntity containing a CustomApiResponse with the list of PostDto
     * representing all posts for the specified user
     */
    @Operation(summary = "Get all Posts by User", description = "Retrieve a list of all posts for a specific user.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts for the specified user.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<CustomApiResponse<List<PostDto>>> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> postDtos = postService.getPostsByUser(userId);
        CustomApiResponse<List<PostDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of posts for user ID: " + userId,
                true,
                postDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    /**
     * Searches for posts by title or content using a keyword.
     *
     * This method searches for posts that match the specified keyword
     * either in their title or content. If no matching posts are found,
     * a ResourceNotFoundException is thrown.
     *
     * @param keyword the keyword to search for in the title or content of the posts
     * @return a ResponseEntity containing a CustomApiResponse with the list of PostDto
     * representing all posts that match the keyword
     */
    @Operation(summary = "Search Posts", description = "Search for posts by title or content using a keyword.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts matching the keyword.")
    @GetMapping("/search")
    public ResponseEntity<CustomApiResponse<List<PostDto>>> searchPosts(@RequestParam String keyword) {
        List<PostDto> postDtos = postService.searchPosts(keyword);
        CustomApiResponse<List<PostDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of posts matching the keyword: " + keyword,
                true,
                postDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    /**
     * Retrieve a list of all posts.
     *
     * This method fetches all post records and returns them as a list of PostDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of PostDto representing all posts
     */
    @Operation(summary = "Get all Posts", description = "Retrieve a list of all posts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<PostDto>>> getAllPosts() {
        List<PostDto> postDtos = postService.getAllPosts();
        CustomApiResponse<List<PostDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of posts.",
                true,
                postDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }










    /**
     * Retrieve a post by their unique ID using the provided PostDto.
     *
     * This method retrieves a post's details based on their ID and returns
     * a CustomApiResponse containing the corresponding PostDto if found.
     * If the post does not exist, it returns a CustomApiResponse with a
     * message indicating that the post was not found and a 404 Not Found status.
     *
     * @param id the ID of the post to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the PostDto and
     *         an HTTP status of OK, or a NOT FOUND status if the post does not exist.
     */
    @Operation(summary = "Get Post by ID", description = "Retrieve a post by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the post.")
    @ApiResponse(responseCode = "404", description = "Post not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<PostDto>> getUserById(@PathVariable Long id) {
        Optional<PostDto> postDto = postService.getPostById(id);
        if (postDto.isPresent()){
            CustomApiResponse<PostDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the post.",
                    true,
                    postDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<PostDto> response = new CustomApiResponse<>(
                    "Post not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new post.
     *
     * This method validates the incoming post data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param postDto the DTO containing the user information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved post data
     */
    @Operation(summary = "Create a new Post", description = "Create a new post record.")
    @ApiResponse(responseCode = "201", description = "Post created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<PostDto>> createPost(@Valid @RequestBody PostDto postDto){
        PostDto savedPost = postService.createPost(postDto);
        CustomApiResponse<PostDto> response = new CustomApiResponse<>(
                "Post created successfully",
                true,
                savedPost
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    /**
     * Update the details of an existing post using the provided PostDto.
     *
     * This method accepts the post's ID and a DTO containing updated post details.
     * It updates the post record if it exists and returns the updated PostDto object.
     *
     * @param id the ID of the post to be updated
     * @param postDto the DTO containing updated post details
     * @return a ResponseEntity containing a CustomApiResponse with the updated PostDto,
     *         or a NOT FOUND response if the post does not exist
     */
    @Operation(summary = "Update post", description = "Update the details of an existing post.")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<PostDto>>  updatePost(
            @PathVariable Long id,
            @RequestBody PostDto postDto) {
        Optional<PostDto> postDtoOptional = postService.getPostById(id);
        if (postDtoOptional.isPresent()) {
            PostDto updatedPost = postService.updatePost(id, postDto);
            CustomApiResponse<PostDto> response = new CustomApiResponse<>(
                    "Post updated successfully",
                    true,
                    updatedPost
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<PostDto> response = new CustomApiResponse<>(
                    "Post not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Delete a post by their ID.
     *
     * This method deletes the post record based on the given ID if it exists.
     *
     * @param id the ID of the post to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the post does not exist
     */
    @Operation(summary = "Delete Post", description = "Delete a post by its ID.")
    @ApiResponse(responseCode = "204", description = "Post deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Post not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deletePost(@PathVariable Long id) {
        Optional<PostDto> postDto = postService.getPostById(id);
        if (postDto.isPresent()) {
            postService.deletePost(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Post deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Post not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }




}
