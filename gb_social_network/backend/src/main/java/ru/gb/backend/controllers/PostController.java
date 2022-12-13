package ru.gb.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.exceptions.ResourceNotFoundException;
import ru.gb.backend.services.AttachmentService;
import ru.gb.backend.services.PostService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Log4j2
public class PostController {
    private PostService postService;

    private AttachmentService attachmentService;

    @Autowired
    public void setPostService (PostService postService,
                                AttachmentService attachmentService){
        this.postService = postService;
        this.attachmentService = attachmentService;
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
        return postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Сообщение не найдено"));
    }

    @PostMapping
    public Post addPost(@RequestBody Post post){
        postService.createOrUpdate(post);
        return post;
    }

    @PostMapping("/add")
    public Post addPostWithAttachment(String jsonPost, @RequestParam("attachment") MultipartFile attachment){
        Post post;
        try {
            post = new ObjectMapper().readValue(jsonPost, Post.class);
        } catch (JsonProcessingException e) {
            log.error("JSON to Post parse error", e);
            throw new RuntimeException(e);
        }
        postService.createOrUpdate(post);
        if (attachment != null) {
            try {
                attachmentService.addAttachment(attachment, post.getId());
            } catch (IOException e) {
                log.error("Problem with new attachment" ,e);
                throw new RuntimeException(e);
            }
        }
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
}
