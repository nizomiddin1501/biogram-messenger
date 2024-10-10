package uz.developers.messenger.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Category DTO is used for transferring category data.")
public class CategoryDto {

    @Schema(description = "Unique ID of the category", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 20, message = "Title must be less than or equal to 20 characters")
    @Schema(description = "Title of the Category. This represents the name or label that describes the category.",
            example = "Technology",
            required = true)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 40, message = "Description must be less than or equal to 40 characters")
    @Schema(description = "A brief description of the Category. This provides additional context or details about what the category includes.",
            example = "Posts related to technological advancements, gadgets, and innovations",
            required = true)
    private String description;









}
