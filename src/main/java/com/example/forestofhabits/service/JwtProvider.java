package com.example.forestofhabits.service;

import com.example.forestofhabits.config.jwt.JwtAuthentication;
import com.example.forestofhabits.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private static final String ACCOUNT_ID = "accountId";
    private static final String USER_NAME = "userName";

    private final SecretKey jwtAccessSecret;

    private final int expirationInMinutes;

    public JwtProvider(
            @Value("${jwt.secret.key}") String jwtAccessSecret,
            @Value("${jwt.expiration}") int expirationInMinutes) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.expirationInMinutes = expirationInMinutes;
    }

    public String generateAccessToken(Account account) {
        final Instant accessExpirationInstant = LocalDateTime.now()
                .plusMinutes(expirationInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject("JWT Auth token")
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim(ACCOUNT_ID, account.getId())
                .claim(USER_NAME, account.getName())
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtAccessSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static JwtAuthentication generateJwtAuthentication(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setAccountId(claims.get(ACCOUNT_ID, Long.class));
        jwtInfoToken.setUserName(claims.get(USER_NAME, String.class));
        return jwtInfoToken;
    }
}
