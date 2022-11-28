package ru.gb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.backend.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p from Post p where p.user.id = ?1 ORDER BY p.date DESC")
    List<Post> getPostsByUserId(Long userId);
}
