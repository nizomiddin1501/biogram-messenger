package uz.developers.messenger.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User DTO is used for transferring user data across the application.")
public class UserDto {


    @Schema(description = "Unique ID of the user", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20, message = "Name must be less than or equal to 20 characters")
    @Schema(description = "Name of the user",
            example = "Nizomiddin Mirzanazarov",
            required = true)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Schema(description = "Email address of the user",
            example = "nizomiddinmirzanazarov@example.com",
            required = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
    @Schema(description = "Password of the user",
            example = "password123",
            required = true)
    private String password;

    @Schema(description = "Brief description about the user",
            example = "A passionate blogger and tech enthusiast.")
    private String about;


}
