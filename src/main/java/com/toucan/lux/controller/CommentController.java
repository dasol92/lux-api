package com.toucan.lux.controller;

import com.toucan.lux.domain.Comment;
import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import com.toucan.lux.dto.comment.CommentDTO;
import com.toucan.lux.dto.comment.CreateCommentReq;
import com.toucan.lux.service.CommentService;
import com.toucan.lux.service.MemberService;
import com.toucan.lux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> commentByPost = commentService.getCommentByPost(postService.getPostById(postId));
        List<CommentDTO> commentDTOList = commentByPost.stream().map(Comment::toDTO).toList();

        return ResponseEntity.ok(commentDTOList);
    }

    @PostMapping("/new/{postId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CreateCommentReq createComment, @PathVariable Long postId) {
        Post postById = postService.getPostById(postId);
        Member author = memberService.getMemberByEmail(createComment.getAuthorEmail());

        Comment newComment = Comment.builder()
                .post(postById)
                .author(author)
                .content(createComment.getContent())
                .likeCount(0L)
                .build();
        Comment comment = commentService.createComment(newComment);
        return ResponseEntity.ok(comment.toDTO());
    }
}
