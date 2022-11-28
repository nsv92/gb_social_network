package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getAllPostsByUserId(@PathVariable Long id) {
        return postService.getAllPostsByUserId(id);
    }
}
