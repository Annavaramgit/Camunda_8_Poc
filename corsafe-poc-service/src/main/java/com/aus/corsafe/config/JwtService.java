package com.aus.corsafe.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {


    private static final String SECRET = "2A3896817DC8FD6EFB98D14D5369E2D0C64C3ED425D46A0A38C0B7F049E90AE998D6EEB3FBF41A30508A964AE5152A221054110A0FC92215B16BBF9CD8143088";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    //genarates token
    public String genarateJwtToken(UserDetails userDetails) {
        return Jwts.builder()

                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(getSignKey())
                .compact();


    }
    //genarates refresh token
    public String genarateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()

                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + 10*1000 * 60 * 60))
                .signWith(getSignKey())
                .compact();


    }



    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Claims getClaims(String jwtToken) {
        //log.info("getClaims token method enetered with token  ::::"+jwtToken);
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

    }


    public String getSubjectFromToken(String jwtToken) {

        Claims claims = getClaims(jwtToken);
        return claims.getSubject();

    }


    public boolean isTokenValid(String jwtToken) {

        Claims claims = getClaims(jwtToken);

        return claims.getExpiration().after(Date.from(Instant.now()));

    }
}
