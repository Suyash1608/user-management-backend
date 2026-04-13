package com.suyash.usermanagement.dto;

import com.suyash.usermanagement.model.Role;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String email;
    private String fullName;
    private Role role;
}
