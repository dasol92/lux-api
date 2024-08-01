package com.toucan.lux.service;

import com.toucan.lux.domain.Book;
import com.toucan.lux.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> saveBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book getBookByNameChapterVerse(String name, Integer chapter, Integer verse) {
        return bookRepository.findByNameAndChapterAndVerse(name, chapter, verse);
    }

}
