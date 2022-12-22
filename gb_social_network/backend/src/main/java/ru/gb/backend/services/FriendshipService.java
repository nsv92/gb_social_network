package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.FriendshipDto;
import ru.gb.backend.entity.Friendship;
import ru.gb.backend.entity.User;
import ru.gb.backend.repositories.FriendshipRepository;
import ru.gb.backend.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private FriendshipRepository friendshipRepository;
    private UserRepository userRepository;

    @Autowired
    public void setFriendshipRepository(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
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

    public String updateFriendship(FriendshipDto friendshipDto) {
        User user = userRepository.findById(friendshipDto.getUserId())
                .orElse(new User());
        User friend = userRepository.findById(friendshipDto.getFriend_id())
                .orElse(new User());
        try {
            friendshipRepository.save(new Friendship(user, friend));
        } catch (RuntimeException e) {
            return "Вы уже друзья c " + user.getName();
        }

        return "Вы подружились c " + user.getName();

    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(new User());
    }
}
