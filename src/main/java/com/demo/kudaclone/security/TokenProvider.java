package com.demo.kudaclone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class TokenProvider {

    private final Key key = Keys.hmacShaKeyFor("pass1234#%AS_manHunter$$@@policeRaid".getBytes(StandardCharsets.UTF_8));
    private final Date validPeriod = new Date(new Date().getTime() + (1000*60*60));
    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();


    public String createToken(String username) {

        return Jwts
                .builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validPeriod)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claim = jwtParser.parseClaimsJws(token).getBody();

        User principal = new User(claim.getSubject(), "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
