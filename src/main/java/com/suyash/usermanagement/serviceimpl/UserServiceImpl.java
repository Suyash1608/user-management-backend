package com.suyash.usermanagement.serviceimpl;

import com.suyash.usermanagement.dto.ChangePasswordRequest;
import com.suyash.usermanagement.dto.UpdateUserRequest;
import com.suyash.usermanagement.dto.UserResponse;
import com.suyash.usermanagement.exception.ResourceNotFoundException;
import com.suyash.usermanagement.model.Role;
import com.suyash.usermanagement.model.User;
import com.suyash.usermanagement.repository.UserRepository;
import com.suyash.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUserById(Long id) {
        return mapToResponse(findOrThrow(id));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return mapToResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByDepartment(String department) {
        return userRepository.findByDepartment(department).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getActiveUsers() {
        return userRepository.findByActive(true).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = findOrThrow(id);
        applyUpdates(user, request);
        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse assignRole(Long id, Role role) {
        User user = findOrThrow(id);
        user.setRole(role);
        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse toggleUserStatus(Long id) {
        User user = findOrThrow(id);
        user.setActive(!user.getActive());
        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        findOrThrow(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResponse getMyProfile(String email) {
        return getUserByEmail(email);
    }

    @Override
    @Transactional
    public UserResponse updateMyProfile(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
        applyUpdates(user, request);
        return mapToResponse(userRepository.save(user));
    }

    // ── Helpers ──────────────────────────────────────────

    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private void applyUpdates(User user, UpdateUserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setDepartment(request.getDepartment());
    }

    private UserResponse mapToResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .department(u.getDepartment())
                .role(u.getRole())
                .active(u.getActive())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}
