package com.toucan.lux.repository;

import com.toucan.lux.domain.Member;
import com.toucan.lux.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(Member author);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
