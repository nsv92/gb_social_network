package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.FriendshipDto;
import ru.gb.backend.entity.Friendship;
import ru.gb.backend.repositories.FriendshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private FriendshipRepository friendshipRepository;

    @Autowired
    public void setFriendshipRepository(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public List<FriendshipDto> getFriendsByUserId(Long id) {
        return convertFriendshipToList(friendshipRepository.findAllFriendsByUserId(id));
    }

    public List<FriendshipDto> getUsersByFriendId(Long id) {
        return convertFriendshipToList(friendshipRepository.findAllUsersByFriendId(id));
    }


    public List<FriendshipDto> getAllFriendsByUserId(Long userId) {
        return convertFriendshipToList(friendshipRepository.findAllFriendsByUserId(userId));
    }

    private List<FriendshipDto> convertFriendshipToList(List<Friendship> friendships) {
        return friendships.stream().map(this::convertFriendshipToDto).collect(Collectors.toList());
    }

    private FriendshipDto convertFriendshipToDto(Friendship friendship) {
        return new FriendshipDto(friendship.getId(), friendship.getUser().getId(), friendship.getFriend().getId());
    }

    public List<FriendshipDto> getAllFriends() {
        return convertFriendshipToList(friendshipRepository.findAll());
    }

    public Friendship updateFriendship(Friendship friendship){
            return friendshipRepository.save(friendship);
    }

}
