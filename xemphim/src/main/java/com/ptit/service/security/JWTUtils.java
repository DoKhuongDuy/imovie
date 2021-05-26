package com.ptit.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ptit.service.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("exp", expiryDate.getTime() / 1000)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public DecodedJWT validateJWT(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
        return decodedJWT;
    }
}
