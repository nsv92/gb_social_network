package ru.gb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.backend.entity.PostAnswer;
import java.util.List;

public interface PostAnswerRepository extends JpaRepository<PostAnswer, Long> {
    List<PostAnswer> findAllPostsByUserId(Long userId);
}
