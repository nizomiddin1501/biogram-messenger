package uz.developers.messenger.service;

import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.exceptions.UserException;
import uz.developers.messenger.payload.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // get all
    List<UserDto> getAllUsers();

    // get by ID
    Optional<UserDto> getUserById(Long userId) throws ResourceNotFoundException;

    // create
    UserDto createUser(UserDto userDto) throws UserException;

    // update
    UserDto updateUser(Long userId, UserDto userDto) throws ResourceNotFoundException;

    // delete
    void deleteUser(Long userId) throws ResourceNotFoundException;



}
