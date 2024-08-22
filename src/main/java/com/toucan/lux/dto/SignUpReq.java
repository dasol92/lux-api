package com.toucan.lux.dto;

import lombok.Data;

@Data
public class SignUpReq {
    private String email;
    private String password;
    private String name;
}
