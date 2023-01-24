package com.chat.app.controller;

import com.chat.app.security.payload.request.*;
import com.chat.app.security.payload.response.JwtResponse;
import com.chat.app.security.payload.response.MessageResponse;
import com.chat.app.security.payload.response.TokenRefreshResponse;
import com.chat.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        return ResponseEntity.ok(authService.changePassword(changePasswordRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(signupRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRefreshRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseEntity.noContent()
                .build();
    }
}
