package com.chat.app.service;

import com.chat.app.exception.BadRequestException;
import com.chat.app.exception.NotFoundException;
import com.chat.app.security.payload.request.*;
import com.chat.app.security.payload.response.JwtResponse;
import com.chat.app.security.payload.response.MessageResponse;
import com.chat.app.security.payload.response.TokenRefreshResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);

    MessageResponse register(SignupRequest signupRequest) throws BadRequestException, NotFoundException;

    TokenRefreshResponse refreshToken (TokenRefreshRequest tokenRefreshRequest);

    boolean logout (LogoutRequest logoutRequest);

    MessageResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
