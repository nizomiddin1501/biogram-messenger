package uz.developers.messenger.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Comment DTO is used for transferring comment data.")
public class CommentDto {


    @Schema(description = "Unique ID of the comment", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Content cannot be blank")
    @Schema(description = "Content of the comment made by the user",
            example = "This is a great post! I learned a lot from it.",
            required = true)
    private String content;

    @Schema(description = "User who made the comment",
            example = "UserDto(id=1, name=Nizomiddin Mirzanazarov, email=nizomiddinmirzanazarov@example.com)",
            required = true)
    private UserDto userDto;

    @Schema(description = "Blog post that this comment is related to",
            example = "PostDto(id=1, title=How to Learn Java, content=Java is a versatile language.)",
            required = true)
    private PostDto postDto;











}
