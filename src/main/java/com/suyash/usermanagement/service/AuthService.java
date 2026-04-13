package com.suyash.usermanagement.service;

import com.suyash.usermanagement.dto.AuthResponse;
import com.suyash.usermanagement.dto.LoginRequest;
import com.suyash.usermanagement.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
