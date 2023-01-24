package com.chat.app.service;

import com.chat.app.exception.dto.BadRequestException;
import com.chat.app.exception.dto.NotFoundException;
import com.chat.app.security.payload.request.LoginRequest;
import com.chat.app.security.payload.request.SignupRequest;
import com.chat.app.security.payload.response.JwtResponse;
import com.chat.app.security.payload.response.MessageResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);

    MessageResponse register(SignupRequest signupRequest) throws BadRequestException, NotFoundException;
}
