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
//       boolean bool = (isExistFriendByKey(friendshipDto.getUserId(), friendshipDto.getFriend_id()));

        User user = userRepository.findById(friendshipDto.getUserId())
                .orElse(new User());
        User friend = userRepository.findById(friendshipDto.getFriend_id())
                .orElse(new User());
        friendshipRepository.save(new Friendship(user, friend));
        return "Вы подружились... ";
//        }else{
//            return  "Вы уже друзья c ";
//        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(new User());
    }

//    Метод для проверки есть ли уже такие друзья
//    public boolean isExistFriendByKey(Long userId, Long friend_id) {
//        Friendship friendship = friendshipRepository.read_UserId_and_Friend_id(userId, friend_id);
//        return Objects.isNull(friendship);
//    }

//    public Friendship getFriendships(Long userId, Long friend_id){
//        User user = userRepository.findById(userId)
//                .orElse(new User());
//        User friend = userRepository.findById(friend_id)
//                .orElse(new User());
//        return new Friendship(user, friend);
//    }
}
