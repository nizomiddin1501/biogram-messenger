package uz.developers.messenger.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.messenger.entity.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {


    // Title exists check
    @Query(value = "select count(*) > 0 from category c where c.content = :title", nativeQuery = true)
    boolean existsByTitle(@Param("title") String title);

}
