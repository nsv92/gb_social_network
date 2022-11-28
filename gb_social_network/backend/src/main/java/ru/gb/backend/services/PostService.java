package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.repositories.PostRepository;

import java.util.List;
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
        return convertPostsToList(postRepository.getPostsByUserId(userId));
    }

    private PostDto convertPostToDto(Post post) {
        return new PostDto(post.getId(), post.getUser(), post.getHead(), post.getDate());
    }

    private List<PostDto> convertPostsToList(List<Post> posts) {
        return posts.stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

}
