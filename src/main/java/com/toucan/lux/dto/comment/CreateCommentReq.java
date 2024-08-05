package com.toucan.lux.dto.comment;

import lombok.Data;

@Data
public class CreateCommentReq {
    private String content;
    private String authorEmail;
}
