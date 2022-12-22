package ru.gb.backend.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.PostService;
import ru.gb.backend.services.UserService;
import ru.gb.backend.services.exception.PostNotFound;


import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Api("Post controller")
public class PostController {
    private final UserService userService;

    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get all post by user id")
    public List<PostDto> getAllPostsByUserId (@PathVariable Long id){
        return postService.getAllPostsByUserId(id);
    }

    @GetMapping("/all")
    @ApiOperation("Get all posts")
    public List<PostDto> getAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping(path="/id/{id}")
    @ApiOperation("Get post by post id")
    public Post findById(@PathVariable("id") Long id){
        return postService.findById(id).orElseThrow(PostNotFound::new);
    }

    @PostMapping
    @ApiOperation("Add post")
    public Post addPost(@RequestBody Post post){
        postService.createOrUpdate(post);
        return post;
    }
    @PutMapping
    @ApiOperation("Post update")
    public Post updatePost(@RequestBody Post post){
        postService.createOrUpdate(post);
        return post;
    }
    @DeleteMapping("/id/{id}")
    @ApiOperation("Delete post by post id")
    public void deleteById(@PathVariable("id") Long id){
        postService.deleteById(id);
    }
    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(PostNotFound e){
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

}
