package com.toucan.lux.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Book {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String name;
    private Integer chapter;
    private Integer verse;
    private String content;
}
