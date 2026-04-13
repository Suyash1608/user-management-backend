package com.suyash.usermanagement.controller;

import com.suyash.usermanagement.dto.ApiResponse;
import com.suyash.usermanagement.dto.UserResponse;
import com.suyash.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderator/users")
@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
@RequiredArgsConstructor
public class ModeratorController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("All users", userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("User found", userService.getUserById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponse>>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search results", userService.searchUsers(keyword)));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(ApiResponse.success("Users in department", userService.getUsersByDepartment(department)));
    }
}
