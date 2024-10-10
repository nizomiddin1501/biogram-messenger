package uz.developers.messenger.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long id;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private UserDto userDto;

    private PostDto postDto;











}
