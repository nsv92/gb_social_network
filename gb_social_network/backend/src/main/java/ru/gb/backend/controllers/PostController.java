package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.services.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void addPost(@RequestBody PostDto post) {
        postService.save(post);
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody String text) {
        postService.update(id, text);
    }

}
