package com.toucan.lux.domain;

import com.toucan.lux.dto.PostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Post extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    private Long likeCount;

    @ManyToMany
    @Builder.Default
    private List<Member> likedMembers = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<Book> references = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public PostDTO toDTO() {
        PostDTO dto = new PostDTO();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setContent(this.content);
        dto.setAuthorName(this.author != null ? this.author.getName() : null);
        dto.setCreatedAt(this.getCreatedAt());
        dto.setUpdatedAt(this.getUpdatedAt());
        dto.setLikeCount(this.likeCount);
        dto.setReferences(this.references.stream().map(Book::getName).toList());
        dto.setComments(this.comments.stream().map(Comment::toDTO).toList());
        return dto;
    }
}
