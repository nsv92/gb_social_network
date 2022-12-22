package ru.gb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.dto.PostAnswerDto;
import ru.gb.backend.entity.PostAnswer;
import ru.gb.backend.exceptions.ResourceNotFoundException;
import ru.gb.backend.services.PostAnswerService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/answer")
public class PostAnswerController {
    private PostAnswerService postAnswerService;

    @Autowired
    public void setPostAnswerService (PostAnswerService postAnswerService){
        this.postAnswerService = postAnswerService;
    }

    @GetMapping("/{id}")
    public List<PostAnswerDto> getAllPostsAnswerByUserId (@PathVariable Long id){
        return postAnswerService.getAllPostsByUserId(id);
    }

    @GetMapping("/all")
    public List<PostAnswer> findAllPostAnswer() {
        return postAnswerService.findAllPosts();
    }

    @GetMapping(path="/id/{id}")
    public PostAnswer findById(@PathVariable("id") Long id){
        return postAnswerService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Сообщение не найдено"));
    }

    @PostMapping
    public PostAnswer addPostAnswer(@RequestBody PostAnswer postAnswer){
        postAnswerService.createOrUpdate(postAnswer);
        return postAnswer;
    }
    @PutMapping
    public PostAnswer updatePostAnswer(@RequestBody PostAnswer postAnswer){
        postAnswerService.createOrUpdate(postAnswer);
        return postAnswer;
    }
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long id){
        postAnswerService.deleteById(id);
    }
}
