package com.example.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService {
    public void JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.exp}") int exp){
        SECRET = secret;
        this.exp = exp;
    }

    public String SECRET;
    private int exp;


    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(String.valueOf(token));
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username,int exp){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username,exp);
    }


    public String createToken(Map<String,Object> claims, String username,int exp){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String getSubject(final String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String refreshToken(final String token, int exp){
        String username = getSubject(token);
        return generateToken(username,exp);
    }


}
