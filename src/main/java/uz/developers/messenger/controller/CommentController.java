package uz.developers.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.messenger.payload.CommentDto;
import uz.developers.messenger.payload.CustomApiResponse;

import uz.developers.messenger.service.CommentService;


import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to Comment operations.
 * This controller provides RESTful endpoints to manage comment records,
 * including creating, updating, retrieving, and deleting comment information.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {


    private final CommentService commentService;

    /**
     * Constructor for CommentController.
     *
     * @param commentService the service to manage user records
     * @Autowired automatically injects the CommentService bean
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }



    /**
     * Retrieve a list of all comments for a specific post.
     *
     * This method fetches all comments associated with a given post ID and returns them as a list of CommentDto.
     *
     * @param postId the ID of the post for which comments are being retrieved
     * @return a ResponseEntity containing a CustomApiResponse with the list of CommentDto representing all comments for the specified post
     */
    @Operation(summary = "Get all Comments by Post ID", description = "Retrieve a list of all comments for specific post.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments for specific post.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CustomApiResponse<List<CommentDto>>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> commentDtos = commentService.getAllCommentsByPostId(postId);
        CustomApiResponse<List<CommentDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of comments for post ID: " + postId,
                true,
                commentDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    /**
     * Retrieve a comment by their unique ID using the provided CommentDto.
     *
     * This method retrieves a comment's details based on their ID and returns
     * a CustomApiResponse containing the corresponding CommentDto if found.
     * If the comment does not exist, it returns a CustomApiResponse with a
     * message indicating that the comment was not found and a 404 Not Found status.
     *
     * @param id the ID of the comment to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CommentDto and
     *         an HTTP status of OK, or a NOT FOUND status if the user does not exist.
     */
    @Operation(summary = "Get Comment by ID", description = "Retrieve a user by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment.")
    @ApiResponse(responseCode = "404", description = "Comment not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CommentDto>> getCommentById(@PathVariable Long id) {
        Optional<CommentDto> commentDto = commentService.getCommentById(id);
        if (commentDto.isPresent()){
            CustomApiResponse<CommentDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the comment.",
                    true,
                    commentDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CommentDto> response = new CustomApiResponse<>(
                    "Comment not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new comment for specific post.
     *
     * This method validates the incoming comment data (received via DTO), links it to the specified post,
     * and saves it to the database if valid.
     *
     * @param postId the ID of the post to which the comment belongs
     * @param commentDto the DTO containing the comment information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved comment data
     */
    @Operation(summary = "Create a new Comment", description = "Create a new comment record for specific post.")
    @ApiResponse(responseCode = "201", description = "User created successfully.")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<CustomApiResponse<CommentDto>> createComment(@PathVariable Long postId,
                                                                       @Valid @RequestBody CommentDto commentDto){
        CommentDto savedComment = commentService.createComment(postId, commentDto);
        CustomApiResponse<CommentDto> response = new CustomApiResponse<>(
                "Comment created successfully for post ID: " + postId,
                true,
                savedComment
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





    /**
     * Update the details of an existing comment using the provided CommentDto.
     *
     * This method accepts the comment's ID and a DTO containing updated region details.
     * It updates the comment record if it exists and returns the updated RegionDto object.
     *
     * @param id the ID of the user to be updated
     * @param commentDto the DTO containing updated user details
     * @return a ResponseEntity containing a CustomApiResponse with the updated UserDto,
     *         or a NOT FOUND response if the user does not exist
     */
    @Operation(summary = "Update comment", description = "Update the details of an existing user.")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CommentDto>>  updateComment(
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {
        Optional<CommentDto> commentDtoOptional = commentService.getCommentById(id);
        if (commentDtoOptional.isPresent()) {
            CommentDto updatedComment = commentService.updateComment(id, commentDto);
            CustomApiResponse<CommentDto> response = new CustomApiResponse<>(
                    "Comment updated successfully",
                    true,
                    updatedComment
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CommentDto> response = new CustomApiResponse<>(
                    "Comment not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Delete a comment by their ID.
     *
     * This method deletes the comment record based on the given ID if it exists.
     *
     * @param id the ID of the comment to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the comment does not exist
     */
    @Operation(summary = "Delete Comment", description = "Delete a comment by its ID.")
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Comment not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteComment(@PathVariable Long id) {
        Optional<CommentDto> commentDto = commentService.getCommentById(id);
        if (commentDto.isPresent()) {
            commentService.deleteComment(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Comment deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Comment not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }




}
