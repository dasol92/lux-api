package com.toucan.lux.config;

import com.toucan.lux.domain.Book;
import com.toucan.lux.domain.Comment;
import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import com.toucan.lux.service.BookService;
import com.toucan.lux.service.CommentService;
import com.toucan.lux.service.MemberService;
import com.toucan.lux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MemberService memberService;
    private final PostService postService;
    private final BookService  bookService;
    private final CommentService commentService;

    @Override
    public void run(ApplicationArguments args) {
        Member member1 = Member.builder()
                .name("김영삼")
                .email("yskim@naver.com")
                .build();

        Member member2 = Member.builder()
                .name("김대중")
                .email("djkim@naver.com")
                .build();

        List<Member> members = memberService.addMembers(List.of(member1, member2));

        Member author = memberService.getMemberByEmail("yskim@naver.com");

        Book ref1 = Book.builder()
                .name("창세기")
                .chapter(3)
                .verse(15)
                .content("내가 너로 여자와 원수가 되게 하고 네 후손도 여자의 후손과 원수가 되게 하리니 여자의 후손은 네 머리를 상하게 할 것이요 너는 그의 발꿈치를 상하게 할 것이니라 하시고")
                .build();

        Book ref2 = Book.builder()
                .name("히브리서")
                .chapter(12)
                .verse(12)
                .content("그러므로 피곤한 손과 연약한 무릎을 일으켜 세우고")
                .build();

        Book ref3 = Book.builder()
                .name("히브리서")
                .chapter(12)
                .verse(13)
                .content("너희 발을 위하여 곧은 길을 만들어 저는 다리로 하여금 어그러지지 않고 고침을 받게 하라")
                .build();

        List<Book> books = List.of(ref1, ref2, ref3);
        bookService.saveBooks(books);


        int numPosts = 20;
        for (int i = 0; i < numPosts; i++) {
            Post post = Post.builder()
                    .author(author)
                    .title("질문 " + (i+1))
                    .content("본문 뜻이 궁금합니다.")
                    .likeCount(10L)
                    .references(books)
                    .build();
            Post savedPost = postService.createPost(post);

            Comment comment1= Comment.builder()
                    .content("이런 뜻 아닐까요? " + (i+1))
                    .author(member2)
                    .post(savedPost)
                    .build();

            Comment comment2 = Comment.builder()
                    .content("네 감사합니다: " + (i+1))
                    .author(member1)
                    .post(savedPost)
                    .build();

            commentService.createComment(comment1);
            commentService.createComment(comment2);
        }
    }


}
