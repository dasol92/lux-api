package com.toucan.lux.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Long likeCount;

    public static class CommentBuilder {
        public CommentBuilder author(Member author) {
            this.author = author;
            if (author != null) {
                author.addComment(this.build());
            }
            return this;
        }

        public CommentBuilder post(Post post) {
            this.post = post;
            if (post != null) {
                post.addComment(this.build());
            }
            return this;
        }
    }

}

