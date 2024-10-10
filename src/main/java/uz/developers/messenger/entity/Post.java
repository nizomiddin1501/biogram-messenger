package uz.developers.messenger.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
@Schema(description = "Post entity represents a blog post created by a user.")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(name = "post_title", length = 100,nullable = false)
    @Schema(description = "Title of the blog post",
            example = "Understanding Dependency Injection in Spring",
            required = true)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    @Schema(description = "Content of the blog post",
            example = "In this post, we will explore the concept of Dependency Injection...",
            required = true)
    private String content;


    @Column(name = "image", length = 255)
    @Schema(description = "Image associated with the blog post",
            example = "http://example.com/image.jpg")
    private String image;


    @Column(name = "date")
    @Schema(description = "Date when the post was created",
            example = "2024-01-01")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Category to which the post belongs",
            example = "Category(id=1, title=Technology, description=All about technology)",
            required = true)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who created the post",
            example = "User(id=1, name=Nizomiddin Mirzanazarov, email=nizomiddinmirzanazarov@example.com)",
            required = true)
    private User user;





}
