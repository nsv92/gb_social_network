package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.PostDto;
import ru.gb.backend.entity.Post;
import ru.gb.backend.entity.User;
import ru.gb.backend.repositories.PostRepository;
import ru.gb.backend.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<PostDto> getAllPostsByUserId(Long userId) {
        return convertPostsToList(postRepository.findAllPostsByUserId(userId));
    }

    public void save(PostDto post) {
        Post result = convertDtoToPost(post);

        postRepository.save(result);
    }

    @Transactional
    public void update(Long id, String text) {
        Post post = postRepository.getReferenceById(id);

        post.setHead(text);
        postRepository.save(post);
    }

    private PostDto convertPostToDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getId(), post.getHead(), post.getDate());
    }

    private Post convertDtoToPost(PostDto post) {
        Post result = new Post();

        result.setId(post.getId());

        result.setHead(post.getHead());

        User user = userRepository.getReferenceById(post.getUserId());
        result.setUser(user);

        result.setDate(post.getDate());

        return result;
    }

    private List<PostDto> convertPostsToList(List<Post> posts) {
        return posts.stream().map(this::convertPostToDto).collect(Collectors.toList());
    }
}
