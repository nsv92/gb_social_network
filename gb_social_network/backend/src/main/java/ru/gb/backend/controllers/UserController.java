package ru.gb.backend.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PasswordDto;
import ru.gb.backend.dto.UserDto;
import ru.gb.backend.entity.User;
import ru.gb.backend.exceptions.ResourceNotFoundException;
import ru.gb.backend.services.EmailService;
import ru.gb.backend.services.PasswordResetTokenService;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Friendship;
import ru.gb.backend.entity.Post;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.FriendshipService;

import ru.gb.backend.services.PostService;
import ru.gb.backend.services.UserService;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Api("User controller")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    private final PostService postService;

    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public UserController(UserService userService, PostService postService, EmailService emailService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.postService = postService;
        this.emailService = emailService;
        this.passwordResetTokenService = passwordResetTokenService;
    }


    @GetMapping("/all")
    public List<User> findAllUsers() {
       return userService.findAllUsers();
    }

    @GetMapping(path="/id/{id}")
    public User findById(@PathVariable("id") Long id){
        return userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto user) {
        return userService.createOrUpdate(user);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user) {
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/passwordRecovery")
    public void passwordRecovery(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Пользователь с таким e-mail не найден.");
        }
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, token);
        emailService.constructAndSendResetTokenEmail(token, user);
    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam String token) {
        boolean result = passwordResetTokenService.validatePasswordResetToken(token);
        return result ? token : "Токен неверен или просрочен";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestBody PasswordDto passwordDto) {

        boolean result = passwordResetTokenService.validatePasswordResetToken(passwordDto.getToken());

        Optional<User> user = passwordResetTokenService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return "Success";
        } else {
            return "Error";
        }
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