package com.toucan.lux.repository;

import com.toucan.lux.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameAndChapterAndVerse(String name, Integer chapter, Integer verse);
}
