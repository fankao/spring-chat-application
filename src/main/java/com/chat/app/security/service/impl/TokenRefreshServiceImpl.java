package com.chat.app.security.service.impl;

import com.chat.app.entity.RefreshToken;
import com.chat.app.entity.User;
import com.chat.app.exception.ApplicationInternalError;
import com.chat.app.exception.BadRequestException;
import com.chat.app.exception.NotFoundException;
import com.chat.app.exception.TokenRefreshException;
import com.chat.app.repository.RefreshTokenRepository;
import com.chat.app.repository.UserRepository;
import com.chat.app.security.jwt.JwtTokenProvider;
import com.chat.app.security.payload.request.TokenRefreshRequest;
import com.chat.app.security.payload.response.TokenRefreshResponse;
import com.chat.app.security.service.TokenRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {
    @Value("${security.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(rollbackOn = DataAccessException.class)
    @Override
    public RefreshToken createRefreshToken(Long userId) throws NotFoundException {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Username or email is not found"));
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            return refreshTokenRepository.save(refreshToken);
        } catch (DataAccessException e) {
            log.error("CREATE REFRESH TOKEN ERROR: {}", e.getMessage());
            throw new ApplicationInternalError(e.getMessage());
        }
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        try {
            refreshTokenRepository.deleteByUserId(userId);
            return true;
        } catch (Exception e) {
            log.error("DELETE USER ERROR {}", e.getMessage());
            return false;
        }
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(),
                    "Refresh token was expired. Please make a new login request");
        }
        return refreshToken;
    }

    @Transactional
    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) throws BadRequestException {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        TokenRefreshResponse tokenRefreshResponse = refreshTokenRepository.findByToken(refreshToken)
                .map(token -> verifyExpiration(token))
                .map(RefreshToken::getUser)
                .map(user -> processTokenRefreshResponse(user))
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
        if (Objects.isNull(tokenRefreshResponse)) {
            throw new BadRequestException("Cannot refresher token. Please sign in again!");
        }
        return tokenRefreshResponse;
    }

    private TokenRefreshResponse processTokenRefreshResponse(User user) {
        String accessToken = jwtTokenProvider.generateTokenFromUsername(user.getUsername());
        String newRefreshToken = rotateRefreshToken(user);
        if (Objects.isNull(newRefreshToken)) {
            return null;
        }
        return new TokenRefreshResponse(accessToken, newRefreshToken);
    }

    private String rotateRefreshToken(User user) {
        try {
            deleteByUserId(user.getId());
            return createRefreshToken(user.getId()).getToken();
        } catch (NotFoundException e) {
            log.error("ROTATE REFRESH TOKEN ERROR: {}", e.getMessage());
            return null;
        }
    }
}
