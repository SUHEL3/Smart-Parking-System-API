package com.example.smartparkingsystemapi.JWTSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "thissecretkeyisusedtogeneratetokenthissecretkeyisnotenoughtogetkeysoaddedsomemorecontent";

    //Get secret key in byte[] array as we required byte array instead of String
    public Key getSignKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
        //hmacShaKeyFor() method converts byte array key into secure key object
    }

    //Generate token
    public String generateToken(
            String email
    ){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Extract claims
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Extract email
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    //Validate token
    public boolean validateToken(String token,String email){
        String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }
    //expiry check
    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

}
