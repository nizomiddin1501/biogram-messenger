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
@Table(name = "comment")
@Schema(description = "Comment entity represents a user's comment on a specific blog post.")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(name = "user_name", length = 20,nullable = false)
    @Schema(description = "Content of the comment made by the user.",
            example = "This is a great post! I learned a lot from it.",
            required = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who made the comment.",
            example = "User(id=1, name=Nizomiddin Mirzanazarov, email=nizomiddinmirzanazarov@example.com)",
            required = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @Schema(description = "Blog post that this comment is related to.",
            example = "Post(id=1, title=How to Learn Java, content=Java is a versatile language.)",
            required = true)
    private Post post;

}
