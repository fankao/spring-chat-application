package com.chat.app.security.service;

import com.chat.app.entity.RefreshToken;
import com.chat.app.exception.dto.BadRequestException;
import com.chat.app.exception.dto.NotFoundException;
import com.chat.app.security.payload.request.TokenRefreshRequest;
import com.chat.app.security.payload.response.TokenRefreshResponse;

public interface TokenRefreshService {
    RefreshToken createRefreshToken(Long userId) throws NotFoundException;
    boolean deleteByUserId(Long userId);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    /**
     * Firstly, we get the Refresh Token from request data
     * Next, get the RefreshToken object {id, user, token, expiryDate} from raw Token using RefreshTokenService
     * We verify the token (expired or not) basing on expiryDate field
     * Continue to use user field of RefreshToken object as parameter to generate new Access Token using JwtUtils
     * Return TokenRefreshResponse if everything is done
     * Or else, throw TokenRefreshException
     * @param tokenRefreshRequest
     * @return
     */
    TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) throws BadRequestException;
}
