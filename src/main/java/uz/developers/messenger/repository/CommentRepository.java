package uz.developers.messenger.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.messenger.entity.Comment;

public interface CommentRepository extends BaseRepository<Comment, Long> {


    // Content exists check
    @Query(value = "select count(*) > 0 from comment c where c.post_title = :content", nativeQuery = true)
    boolean existsByContent(@Param("content") String content);



}
