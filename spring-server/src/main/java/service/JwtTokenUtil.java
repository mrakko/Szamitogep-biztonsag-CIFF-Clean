package service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
    
    @Value("${jwt.secret}")
    private String secret;
    
    private final int tokenExpiryTime = 24 * 60 * 60 * 60 * 1000; //1 nap

    public Optional<String> getTokenFromHeader(String authHeader) {
        if (authHeader != null) {
            String[] components = authHeader.split("\\s+");
            if (components.length != 2 || !components[0].equals("Bearer"))
                return Optional.empty();
            return Optional.of(components[1]);
        }
        return Optional.empty();
    }

    public boolean isValidToken(String token, Long userId) {
        Claims claims = getAllClaimsFromToken(token);
        return !isExpired(claims)
                && isUserIdValid(claims, userId);
    }

    private boolean isUserIdValid(Claims claims, Long userId) {
        return userId.equals(doGetUserId(claims));
    }

    private Long doGetUserId(Claims claims) {
        Object idObject = claims.get("userId");
        return idObject instanceof Integer
                ? Long.valueOf((Integer)idObject)
                : (Long) idObject;
    }

    public String generateToken(Long userId, String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return doGenerateToken(claims, userName);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiryTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    private boolean isExpired(Claims claims) {
        return new Date(System.currentTimeMillis()).after(claims.getExpiration());
    }
}