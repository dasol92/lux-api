package com.toucan.lux.service;

import com.toucan.lux.domain.Comment;
import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @DisplayName("게시물을 생성한다")
    @Test
    @Transactional
    @Rollback(value = false)
    void createPost() {
        // given
        Member member1 = Member.builder()
                .name("김서영")
                .build();

        Member member2 = Member.builder()
                .name("김다솔")
                .build();

        memberService.addMember(member1);
        memberService.addMember(member2);

        Post post = Post.builder()
                .author(member1)
                .title("질문이요")
                .content("이거 어떻게 해요?")
                .likeCount(10L)
                .build();

        Comment comment1 = Comment.builder()
                .author(member1)
                .content("댓글이에요")
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .author(member2)
                .content("좋아요")
                .post(post)
                .build();

        // when
        Post savedPost = postService.createPost(post);

        // then
        List<Post> postsByAuthor = postService.getPostsByAuthor(member1);
        assertThat(postsByAuthor).contains(savedPost);
    }

    @DisplayName("모든 게시물을 조회한다")
    @Test
    void getAllPosts() {
        // given

        // when

        // then
    }


    @Test
    void getPostById() {
    }

    @Test
    void deletePostById() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void getPostsByAuthorId() {
    }
}