package com.toucan.lux.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String authorName;
    private String authorEmail;
    private LocalDateTime createdAt;
    private Long likeCount;

}