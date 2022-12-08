package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.entity.User;
import ru.gb.backend.services.PostService;
import ru.gb.backend.services.exception.PostNotFound;
import ru.gb.backend.services.exception.UserNotFound;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public void setPostService (PostService postService){
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public List<PostDto> getAllPostsByUserId (@PathVariable Long id){
        return postService.getAllPostsByUserId(id);
    }

    @GetMapping("/all")
    public List<Post> findAllPost() {
        return postService.findAllPosts();
    }

    @GetMapping(path="/id/{id}")
    public Post findById(@PathVariable("id") Long id){
        return postService.findById(id).orElseThrow(PostNotFound::new);
    }

    @PostMapping
    public Post addPost(@RequestBody Post post){
        postService.createOrUpdate(post);
        return post;
    }
    @PutMapping
    public Post updatePost(@RequestBody Post post){
        postService.createOrUpdate(post);
        return post;
    }
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long id){
        postService.deleteById(id);
    }
    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(PostNotFound e){
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }
}
