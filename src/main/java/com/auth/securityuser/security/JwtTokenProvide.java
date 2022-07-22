package com.auth.securityuser.security;

import com.auth.securityuser.domain.User;
import com.auth.securityuser.exceptions.HttpResponseException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvide {
    private static final String TOKEN_SIGNATURE_NOT_VALIDATED = "Token signature not validated";
    private static final String INVALID_TOKEN = "Invalid token";
    private static final String EXPIRED_TOKEN = "Expired token";
    private static final String UNSUPPORTED_TOKEN = "Unsupported token";
    private static final String CLAIMS_JWT_EMPTY = "Claims jwt empty";

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-millisecond}")
    private int jwtExpirationInMs;

    public String createToken(Authentication authentication, User user) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId",user.getAccountId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());

        return Jwts.builder().setSubject(username).addClaims(claims).setIssuedAt(currentDate).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUsernameByJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, TOKEN_SIGNATURE_NOT_VALIDATED);
        } catch (MalformedJwtException ex) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, CLAIMS_JWT_EMPTY);
        }
    }
}
