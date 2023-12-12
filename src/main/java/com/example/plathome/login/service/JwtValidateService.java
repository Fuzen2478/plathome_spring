package com.example.plathome.login.service;


import com.example.plathome.global.constant.JwtStaticField;
import com.example.plathome.login.domain.AccessSecretKey;
import com.example.plathome.login.domain.RefreshSecretKey;
import com.example.plathome.login.exception.ExpiredAccessTokenException;
import com.example.plathome.login.exception.ExpiredRefreshTokenException;
import com.example.plathome.login.exception.InvalidAccessTokenException;
import com.example.plathome.login.exception.InvalidRefreshTokenException;
import com.example.plathome.login.service.redis.RefreshTokenRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Enumeration;


@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final AccessSecretKey accessSecretKey;
    private final RefreshSecretKey refreshSecretKey;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public String validateAccessToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(JwtStaticField.ACCESS_HEADER);
            String token = authHeader.substring(JwtStaticField.BEARER.length());
            Claims claims = this.validateToken(token, accessSecretKey.getDecoded());
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    public String validateRefreshToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(JwtStaticField.REFRESH_HEADER);
            String token = authHeader.substring(JwtStaticField.BEARER.length());
            Claims claims = this.validateToken(token, refreshSecretKey.getDecoded());
            String memberId = claims.getSubject();
            this.validateIsInRedisRefreshToken(memberId, token);
            return memberId;
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private Claims validateToken(String token, byte[] decodedSecretKey) {
        javax.crypto.SecretKey key = Keys.hmacShaKeyFor(decodedSecretKey);

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateIsInRedisRefreshToken(String memberId, String token) {
        if (!refreshTokenRedisService.checkExistValue(memberId)) {
            throw new ExpiredRefreshTokenException();
        } else if (!refreshTokenRedisService.getData(memberId).equals(token)) {
            throw new InvalidRefreshTokenException();
        }
    }
}
