package uz.developers.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.messenger.payload.CustomApiResponse;
import uz.developers.messenger.payload.UserDto;
import uz.developers.messenger.service.UserService;

import java.util.List;
import java.util.Optional;
/**
 * Controller for handling requests related to User operations.
 * This controller provides RESTful endpoints to manage user records,
 * including creating, updating, retrieving, and deleting user information.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    /**
     * Constructor for RegionController.
     *
     * @param userService the service to manage user records
     * @Autowired automatically injects the RegionService bean
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    /**
     * Retrieve a list of all users.
     *
     * This method fetches all user records and returns them as a list of UserDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of UserDto representing all users
     */
    @Operation(summary = "Get all Users", description = "Retrieve a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        CustomApiResponse<List<UserDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of users.",
                true,
                userDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    /**
     * Retrieve a user by their unique ID using the provided UserDto.
     *
     * This method retrieves a user's details based on their ID and returns
     * a CustomApiResponse containing the corresponding UserDto if found.
     * If the user does not exist, it returns a CustomApiResponse with a
     * message indicating that the user was not found and a 404 Not Found status.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the UserDto and
     *         an HTTP status of OK, or a NOT FOUND status if the user does not exist.
     */
    @Operation(summary = "Get User by ID", description = "Retrieve a user by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the user.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        if (userDto.isPresent()){
            CustomApiResponse<UserDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the user.",
                    true,
                    userDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<UserDto> response = new CustomApiResponse<>(
                    "User not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new user.
     *
     * This method validates the incoming user data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param userDto the DTO containing the user information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved user data
     */
    @Operation(summary = "Create a new User", description = "Create a new user record.")
    @ApiResponse(responseCode = "201", description = "User created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<UserDto>> createUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        CustomApiResponse<UserDto> response = new CustomApiResponse<>(
                "User created successfully",
                true,
                savedUser
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    /**
     * Update the details of an existing user using the provided UserDto.
     *
     * This method accepts the user's ID and a DTO containing updated user details.
     * It updates the user record if it exists and returns the updated UserDto object.
     *
     * @param id the ID of the user to be updated
     * @param userDto the DTO containing updated user details
     * @return a ResponseEntity containing a CustomApiResponse with the updated UserDto,
     *         or a NOT FOUND response if the user does not exist
     */
    @Operation(summary = "Update user", description = "Update the details of an existing user.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UserDto>>  updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        Optional<UserDto> userDtoOptional = userService.getUserById(id);
        if (userDtoOptional.isPresent()) {
            UserDto updatedUser = userService.updateUser(id, userDto);
            CustomApiResponse<UserDto> response = new CustomApiResponse<>(
                    "User updated successfully",
                    true,
                    updatedUser
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<UserDto> response = new CustomApiResponse<>(
                    "User not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Delete a user by their ID.
     *
     * This method deletes the user record based on the given ID if it exists.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the user does not exist
     */
    @Operation(summary = "Delete User", description = "Delete a user by its ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteUser(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        if (userDto.isPresent()) {
            userService.deleteUser(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "User deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "User not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }




}
