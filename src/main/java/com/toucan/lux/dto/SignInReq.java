package com.toucan.lux.dto;

import lombok.Data;

@Data
public class SignInReq {
    private String email;
    private String password;
}
