package com.example.ciffclean.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
    
    @Value("${jwt.secret}")
    private String secret = "Secret";
    
    private final int tokenExpiryTime = 10 * 60 * 60 * 1000; //10 perc

    public Long getCurrentUserId(String authorization){
        var id = getUserIdFromToken(authorization);
        if(id == null){
            throw new NoSuchElementException(MediaService.USER_NOT_FOUND);
        }
        return id.get();
    }

    public Optional<String> getTokenFromHeader(String authHeader) {
        if (authHeader != null) {
            String[] components = authHeader.split("\\s+");
            if (components.length != 2 || !components[0].equals("Bearer"))
                return Optional.empty();
            return Optional.of(components[1]);
        }
        return Optional.empty();
    }

    public Optional<Long> getUserIdFromToken(String authHeader){
        var token = getTokenFromHeader(authHeader);
        if(!token.isEmpty()){
            Claims claims = getAllClaimsFromToken(token.get());
            if(isExpired(claims)) {return Optional.empty();}
            var res = doGetUserId(claims);
            return Optional.of(res);
        }
        return Optional.empty();
    }

    public void checkIfUserIsAuthenticated(String authHeader){
        var userId = getUserIdFromToken(authHeader);
        if(userId.isEmpty()){
           throw new NoSuchElementException(MediaService.USER_NOT_FOUND); 
        }
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
    
    public Long getUserId(String token){
        Claims claims = getAllClaimsFromToken(token);
        return doGetUserId(claims);
    }

}
