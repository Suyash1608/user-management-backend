package com.suyash.usermanagement.service;

import com.suyash.usermanagement.dto.ChangePasswordRequest;
import com.suyash.usermanagement.dto.UpdateUserRequest;
import com.suyash.usermanagement.dto.UserResponse;
import com.suyash.usermanagement.model.Role;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(Role role);
    List<UserResponse> getUsersByDepartment(String department);
    List<UserResponse> getActiveUsers();
    List<UserResponse> searchUsers(String keyword);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse assignRole(Long id, Role role);
    UserResponse toggleUserStatus(Long id);
    void deleteUser(Long id);
    void changePassword(String email, ChangePasswordRequest request);
    UserResponse getMyProfile(String email);
    UserResponse updateMyProfile(String email, UpdateUserRequest request);
}
