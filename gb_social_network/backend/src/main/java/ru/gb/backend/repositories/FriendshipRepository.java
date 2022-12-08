package ru.gb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.entity.Friendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long>{
    List<Friendship> findAllFriendsByUserId(Long userId);
    List<Friendship> findAllUsersByFriendId(Long friendId);
}
