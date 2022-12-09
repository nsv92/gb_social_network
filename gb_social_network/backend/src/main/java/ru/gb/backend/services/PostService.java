package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.entity.User;
import ru.gb.backend.repositories.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDto> getAllPostsByUserId(Long userId) {
        return convertPostsToList(postRepository.findAllPostsByUserId(userId));
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post createOrUpdate(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    private PostDto convertPostToDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getId(), post.getHead(), post.getDate());
    }

    private List<PostDto> convertPostsToList(List<Post> posts) {
        return posts.stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

}
