package com.toucan.lux.service;

import com.toucan.lux.domain.Comment;
import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    private final String testMember1Email = "yskim@naver.com";
    private final String testMember2Email = "djkim@naver.com";

    @BeforeAll
    static void setUpAll(@Autowired MemberService memberService) {
        Member member1 = Member.builder()
                .name("김영삼")
                .email("yskim@naver.com")
                .build();

        Member member2 = Member.builder()
                .name("김대중")
                .email("djkim@naver.com")
                .build();

        memberService.addMember(member1);
        memberService.addMember(member2);
    }

    @DisplayName("게시물을 생성한다")
    @Test
    @Transactional
    void createPost() {
        // given
        Member author = memberService.getMemberByEmail(testMember1Email);
        Post post = Post.builder()
                .author(author)
                .title("질문이요")
                .content("이거 어떻게 해요?")
                .likeCount(10L)
                .build();

        // when
        Post savedPost = postService.createPost(post);

        // then
        List<Post> postsByAuthor = postService.getPostsByAuthor(author);
        assertThat(postsByAuthor).contains(savedPost);
    }

    @DisplayName("댓글이 달린 게시물을 생성한다")
    @Test
    @Transactional
    void test() {
        // given
        Member author = memberService.getMemberByEmail(testMember1Email);
        Member commenter = memberService.getMemberByEmail(testMember2Email);
        Post post = Post.builder()
                .author(author)
                .title("질문이요")
                .content("이거 어떻게 해요?")
                .likeCount(10L)
                .build();

        Comment comment = Comment.builder()
                .post(post)
                .author(commenter)
                .content("이렇게 하면 됩니다.")
                .build();

        // when
        commentService.createComment(comment);
        postService.createPost(post);

        // then
        List<Comment> commentsByPost = commentService.getCommentByPost(post);
        assertThat(commentsByPost)
                .hasSize(1)
                .contains(comment);

    }

    @DisplayName("게시물 삭제")
    @Test
    @Transactional
    void deletePostById() {
        // given
        Member author = memberService.getMemberByEmail(testMember1Email);
        Post post = Post.builder()
                .author(author)
                .title("질문이요")
                .content("이거 어떻게 해요?")
                .likeCount(10L)
                .build();

        // when
        Post savedPost = postService.createPost(post);
        em.flush();
        em.clear();

        postService.deletePostById(savedPost.getId());
        em.flush();
        em.clear();

        // then
        assertThat(postService.getPostById(savedPost.getId())).isNull();
    }
}