package uz.developers.messenger.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.messenger.entity.Post;

import java.util.List;

public interface PostRepository extends BaseRepository<Post,Long> {



    // Title exists check
    @Query(value = "select count(*) > 0 from posts p where p.post_title = :title", nativeQuery = true)
    boolean existsByTitle(@Param("title") String title);


    // Content exists check
    @Query(value = "select count(*) > 0 from posts p where p.content = :content", nativeQuery = true)
    boolean existsByContent(@Param("content") String content);


    // Check both column
    @Query(value = "select count(*) > 0 from posts p where p.post_title = :title or p.content = :content", nativeQuery = true)
    boolean existsByTitleOrContent(@Param("title") String title, @Param("content") String content);



    ///
    // Query to get posts by category ID
    @Query(value = "select * from posts p where p.category_id = :categoryId", nativeQuery = true)
    List<Post> findByCategoryId(@Param("categoryId") Long categoryId);


    // Query to get posts by user ID
    @Query(value = "select * from posts p where p.user_id = :userId", nativeQuery = true)
    List<Post> findByUserId(@Param("userId") Long userId);


    // Query to search posts by title or content
    @Query(value = "select * from posts p where p.post_title LIKE %:keyword% OR p.content LIKE %:keyword%", nativeQuery = true)
    List<Post> searchByTitleOrContent(@Param("keyword") String keyword);


}
