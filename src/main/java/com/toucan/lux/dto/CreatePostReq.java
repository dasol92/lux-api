package com.toucan.lux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostReq {
    private String title;
    private String content;
//    private Long authorId;

}
