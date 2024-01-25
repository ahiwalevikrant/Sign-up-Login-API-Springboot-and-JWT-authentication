package com.restaurant.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public  static final String SECRET="1234567890asdfghj6drtfygkwqx3cw4e5vr6utbygvfcytxa3ywc4e65rvyftrcsyc4dyhvt ";
    public String generateToken(String email){
        Map<String,Object> claims= new HashMap<>();
    return createtoken(claims,email);
    }

    private String createtoken( Map<String,Object> claims,String email){
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 * 30 ))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(){
        byte [] keyBytes= Decoders.BASE64.decode(SECRET);
     return Keys.hmacShaKeyFor(keyBytes);
    }
}

