package com.mtit.app.service;

import com.mtit.app.dto.AuthResponse;
import com.mtit.app.dto.LoginRequest;
import com.mtit.app.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
