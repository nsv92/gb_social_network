package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.PostAnswerDto;
import ru.gb.backend.entity.PostAnswer;
import ru.gb.backend.repositories.PostAnswerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostAnswerService {

    private PostAnswerRepository postAnswerRepository;

    @Autowired
    public void setPostAnswerRepository(PostAnswerRepository postAnswerRepository) {
        this.postAnswerRepository = postAnswerRepository;
    }

    public List<PostAnswerDto> getAllPostsByUserId(Long userId) {
        return convertPostsToList(postAnswerRepository.findAllPostsByUserId(userId));
    }

    public List<PostAnswer> findAllPosts() {
        return postAnswerRepository.findAll();
    }

    public PostAnswer createOrUpdate(PostAnswer postAnswer){
        return postAnswerRepository.save(new PostAnswer());
    }

    public Optional<PostAnswer> findById(Long id){
        return postAnswerRepository.findById(id);
    }

    public void deleteById(Long id){
        postAnswerRepository.deleteById(id);
    }

    private PostAnswerDto convertPostAnswerToDto(PostAnswer postAnswer) {
        return new PostAnswerDto(postAnswer.getId(), postAnswer.getUser().getId(), postAnswer.getHead(), postAnswer.getDate());
    }

    private List<PostAnswerDto> convertPostsToList(List<PostAnswer> posts) {
        return posts.stream().map(this::convertPostAnswerToDto).collect(Collectors.toList());
    }
}
