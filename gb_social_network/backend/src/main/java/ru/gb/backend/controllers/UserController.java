package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.PostService;
import ru.gb.backend.services.UserService;
import ru.gb.backend.services.exception.UserNotFound;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

//    @GetMapping("/{id}/posts")
//    public List<PostDto> getAllPostsByUserId(@PathVariable Long id) {
//        return postService.getAllPostsByUserId(id);
//    }

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path="/id/{id}")
    public User findById(@PathVariable("id") Long id){
        return userService.findById(id).orElseThrow(UserNotFound::new);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        userService.createOrUpdate(user);
        return user;
    }
    @PutMapping
    public User updateUser(@RequestBody User user){
        userService.createOrUpdate(user);
        return user;
    }
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }
    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(UserNotFound e){
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}