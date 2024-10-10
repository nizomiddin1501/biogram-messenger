package uz.developers.messenger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.messenger.entity.User;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.exceptions.UserException;
import uz.developers.messenger.payload.UserDto;
import uz.developers.messenger.repository.UserRepository;
import uz.developers.messenger.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final ModelMapper modelMapper;


    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        // Convert User entity to UserDto
        UserDto userDto = userToDto(user);
        return Optional.ofNullable(userDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // 1. Convert DTO to entity
        User user = dtoToUser(userDto);

        // 2. Perform business checks on the entity
        if (user.getName() == null || user.getEmail() == null){
            throw new UserException("User name and email must not be null");
        }

        // 3. Checking that the email column does not exist
        boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            throw new UserException("User with this email already exists");
        }

        // 4. Save User
        User savedUser = userRepository.save(user);

        // 4. Convert the saved User to DTO and return
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        // Use ModelMapper to map DTO to entity
        User userDetails = dtoToUser(userDto);

        // update user details
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());
        existingUser.setAbout(userDetails.getAbout());

        // Save updated user
        User updatedUser = userRepository.save(existingUser);

        // Convert updated user entity to DTO and return
        return userToDto(updatedUser);
    }


    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        userRepository.delete(user);
    }


    // Conversion
    // DTO ---> Entity
    private User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // Entity ---> DTO
    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
