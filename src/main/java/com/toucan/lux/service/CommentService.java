package com.toucan.lux.service;

import com.toucan.lux.domain.Comment;
import com.toucan.lux.domain.Post;
import com.toucan.lux.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    public Post getPostByComment(Comment comment) {
        return comment.getPost();
    }

    public List<Comment> getCommentByPost(Post post) {
        return commentRepository.findByPost(post);
    }

}
