package com.suyash.usermanagement.dto;

import com.suyash.usermanagement.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

// ── Register ──────────────────────────────────────────────
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;
}
