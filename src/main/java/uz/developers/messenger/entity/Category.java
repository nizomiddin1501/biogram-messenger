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
@Table(name = "category")
@Schema(description = "Category entity represents a classification or type under which blog posts or content are organized.")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(name = "name", length = 20,nullable = false)
    @Schema(description = "Title of the Category. This represents the name or label that describes the category.",
            example = "Technology",
            required = true)
    private String title;

    @Column(name = "description", length = 40,nullable = false)
    @Schema(description = "A brief description of the Category. This provides additional context or details about what the category includes.",
            example = "Posts related to technological advancements, gadgets, and innovations",
            required = true)
    private String description;



}
