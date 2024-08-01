package com.toucan.lux.domain;

import com.toucan.lux.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author")
    @Builder.Default
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @Builder.Default
    private final List<Comment> comments = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
