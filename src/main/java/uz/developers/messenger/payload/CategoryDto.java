package uz.developers.messenger.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 20, message = "Title must be less than or equal to 20 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 40, message = "Description must be less than or equal to 40 characters")
    private String description;









}
