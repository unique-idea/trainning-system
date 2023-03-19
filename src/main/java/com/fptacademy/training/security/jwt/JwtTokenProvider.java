package com.fptacademy.training.security.jwt;

import com.fptacademy.training.config.ApplicationProperties;
import com.fptacademy.training.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final UserService userService;
    private final long accessExpireTimeInMinutes;
    private final long refreshExpireTimeInMinutes;
    private final JwtBuilder accessJwtBuilder;
    private final JwtParser accessJwtParser;
    private final JwtBuilder refreshJwtBuilder;
    private final JwtParser refreshJwtParser;

    public JwtTokenProvider(ApplicationProperties properties, UserService userService) {
        this.userService = userService;
        accessExpireTimeInMinutes = properties.getAccessExpireTimeInMinutes();
        refreshExpireTimeInMinutes = properties.getRefreshExpireTimeInMinutes();
        Key accessKey = Keys.hmacShaKeyFor(properties.getAccessSecretKey().getBytes(StandardCharsets.UTF_8));
        accessJwtBuilder = Jwts.builder()
                .signWith(accessKey, SignatureAlgorithm.HS256);
        accessJwtParser = Jwts.parserBuilder()
                .setSigningKey(accessKey).build();
        Key refreshKey = Keys.hmacShaKeyFor(properties.getRefreshSecretKey().getBytes(StandardCharsets.UTF_8));
        refreshJwtBuilder = Jwts.builder()
                .signWith(refreshKey, SignatureAlgorithm.HS256);
        refreshJwtParser = Jwts.parserBuilder()
                .setSigningKey(refreshKey).build();
    }

    public String generateAccessToken(Authentication authentication) {
        String email = authentication.getName();
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expiredTime = new Date((new Date()).getTime() + 1000 * 60 * accessExpireTimeInMinutes);
        return accessJwtBuilder
                .setSubject(email)
                .claim("auth", authorities)
                .setExpiration(expiredTime)
                .compact();
    }

    public String generateAccessToken(String refreshToken) {
        Claims claims = refreshJwtParser.parseClaimsJws(refreshToken).getBody();
        String email = claims.getSubject();
        String authorities = userService.getUserPermissionsByEmail(email)
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expiredTime = new Date((new Date()).getTime() + 1000 * 60 * accessExpireTimeInMinutes);
        return accessJwtBuilder
                .setSubject(email)
                .claim("auth", authorities)
                .setExpiration(expiredTime)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date expiredTime = new Date((new Date()).getTime() + 1000 * 60 * refreshExpireTimeInMinutes);
        return refreshJwtBuilder
                .setSubject(email)
                .setExpiration(expiredTime)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = accessJwtParser.parseClaimsJws(accessToken).getBody();
        String email = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities = Arrays.
                stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(email, accessToken, authorities);
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            accessJwtParser.parseClaimsJws(accessToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            refreshJwtParser.parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
