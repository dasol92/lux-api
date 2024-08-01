package com.toucan.lux.controller;

import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import com.toucan.lux.dto.PostDTO;
import com.toucan.lux.dto.CreatePostReq;
import com.toucan.lux.service.MemberService;
import com.toucan.lux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts()
                .stream()
                .map(Post::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post.toDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostReq reqDto) {
        Member member = memberService.getMemberByEmail("djkim@naver.com");
        Post post = Post.builder()
                .author(member) // FIXME
                .title(reqDto.getTitle())
                .content(reqDto.getContent())
                .build();
        // Set other fields as necessary
        return ResponseEntity.ok(postService.createPost(post).toDTO());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }


}
