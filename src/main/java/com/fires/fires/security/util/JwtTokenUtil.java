package com.fires.fires.security.util;

import com.fires.fires.security.dto.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtTokenUtil {

    private final String secret;
    private final long tokenExpirationTime;
    private Key key;
    public static final String HEADER_PREFIX = "Bearer ";
    public static final String TOKEN_EXPIRED = "expired";

    public JwtTokenUtil(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.token-time}") long tokenExpirationTime) {
        this.secret = secret;
        this.tokenExpirationTime = TimeUnit.MINUTES.toMillis(tokenExpirationTime);
    }



    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserPrincipal userPrincipal) {

        long currentTime = Instant.now().get(ChronoField.MILLI_OF_SECOND);
        Date validity = new Date(currentTime + this.tokenExpirationTime);

        return Jwts.builder()
                   .setSubject(userPrincipal.getUsername())
                   .setIssuer("project-fire")
                   .claim("id", userPrincipal.id())
                   .claim("userProviderId", userPrincipal.userProviderId())
                   .claim("username", userPrincipal.username())
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setExpiration(validity)
                   .compact();
    }

    /**
     * 토큰 유효성 검사
     * @param token 검사하고자 하는 토큰
     * @return 토큰 유효시 true 반환, 유효하지 않을 경우 false 반환
     */
    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.debug("잘못된 JWT 토큰 : {}", token);
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰 : {}", token);
        } catch (UnsupportedJwtException e) {
            log.debug("지원되지 않는 JWT 토큰 : {}", token);
        } catch (IllegalArgumentException e) {
            log.debug("유효한 토큰변수가 전달되지 않음 : {}", token);
        }
        return false;
    }
}
