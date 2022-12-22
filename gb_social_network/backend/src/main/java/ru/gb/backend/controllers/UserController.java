package ru.gb.backend.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.PostService;
import ru.gb.backend.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Api("User controller")
public class UserController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{id}/posts")
    @ApiOperation("Get all post by user id")
    public List<PostDto> getAllPostsByUserId(@PathVariable Long id) {
        return postService.getAllPostsByUserId(id);
    }

    @GetMapping("/all")
    @ApiOperation("Get all user")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get user by id")
    public Optional<User> getUserForId(@PathVariable Long id) {
        return userService.getUserForId(id);
    }

}