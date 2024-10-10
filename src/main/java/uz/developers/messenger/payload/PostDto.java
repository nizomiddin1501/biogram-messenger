package uz.developers.messenger.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Post DTO is used for transferring post data across the application.")
public class PostDto {

    @Schema(description = "Unique ID of the post", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    @Schema(description = "Title of the blog post",
            example = "Understanding Dependency Injection in Spring",
            required = true)
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Schema(description = "Content of the blog post",
            example = "In this post, we will explore the concept of Dependency Injection...",
            required = true)
    private String content;


    @Schema(description = "Date when the post was created",
            example = "2024-01-01")
    private Date date;

    @Schema(description = "Image associated with the blog post",
            example = "http://example.com/image.jpg")
    private String image;

    @Schema(description = "Category to which the post belongs",
            example = "CategoryDto(id=1, title=Technology, description=All about technology)",
            required = true)
    private CategoryDto categoryDto;

    @Schema(description = "User who created the post",
            example = "UserDto(id=1, name=Nizomiddin Mirzanazarov, email=nizomiddinmirzanazarov@example.com)",
            required = true)
    private UserDto userDto;
}
