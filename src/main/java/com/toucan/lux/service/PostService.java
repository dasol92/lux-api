package com.toucan.lux.service;

import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import com.toucan.lux.dto.PostDTO;
import com.toucan.lux.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable).map(Post::toDTO);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getPostsByAuthor(Member author) {
        return postRepository.findByAuthor(author);
    }


}
