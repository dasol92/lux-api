package com.toucan.lux.controller;

import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import com.toucan.lux.dto.CreatePostReq;
import com.toucan.lux.dto.PostDTO;
import com.toucan.lux.service.MemberService;
import com.toucan.lux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostDTO> posts = postService.getAllPosts(pageRequest);

        Map<String, Object> responese = new HashMap<>();
        responese.put("content", posts.getContent());
        responese.put("totalPages", posts.getTotalPages());
        responese.put("totalElements", posts.getTotalElements());
        responese.put("size", posts.getSize());
        responese.put("number", posts.getNumber());

        return ResponseEntity.ok(responese);
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
