package com.toucan.lux.dto;

import com.toucan.lux.enums.Role;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private Role role;
}