package uz.developers.messenger.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "User entity represents a registered user in the blog application.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(name = "user_name", length = 20,nullable = false)
    @Schema(description = "Name of the user",
            example = "Nizomiddin Mirzanazarov",
            required = true)
    private String name;

    @Column(name = "user_email", length = 30, nullable = false, unique = true)
    @Schema(description = "Email address of the user",
            example = "johndoe@example.com",
            required = true)
    private String email;

    @Column(name = "user_password", length = 50, nullable = false)
    @Schema(description = "Password of the user",
            example = "password123",
            required = true)
    private String password;

    @Column(name = "about", length = 100)
    @Schema(description = "Brief description about the user",
            example = "A passionate blogger and tech enthusiast.")
    private String about;








}
