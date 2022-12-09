package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.FriendshipDto;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Friendship;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.FriendshipService;
import ru.gb.backend.services.PostService;
import ru.gb.backend.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {
    private final UserService userService;
    private final PostService postService;
    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(UserService userService, PostService postService, FriendshipService friendshipService) {
        this.userService = userService;
        this.postService = postService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/friend/{id}")
    public List<FriendshipDto> getFriendsByUserId(@PathVariable Long id) {
        return friendshipService.getFriendsByUserId(id);
    }

    @GetMapping("/users/{id}")
    public List<FriendshipDto> getUsersByFriendId(@PathVariable Long id) {
        return friendshipService.getUsersByFriendId(id);
    }

    @GetMapping("/all")
    public List<FriendshipDto> getALlFriends() {
        return friendshipService.getAllFriends();
    }

    // Чтение постов моих друзей
    @GetMapping("/post/{id}")
    public List<PostDto> getPostsByUserId(@PathVariable Long id) {
        return postService.getAllPostsByUserId(id);
    }

    @PostMapping
    public Friendship updateFriendship(@RequestBody FriendshipDto friendshipDto) {
        Friendship friendship = new Friendship();
//        User user = new User(userService.getUserForId(friendshipDto.getUserId()));
//        User friend = new User(userService.getUserForId(friendshipDto.getUserId());
//        friendship.setUser(user);
//        friendship.setFriend(friendship.getFriend());
        return friendshipService.updateFriendship(friendship);
    }
}
