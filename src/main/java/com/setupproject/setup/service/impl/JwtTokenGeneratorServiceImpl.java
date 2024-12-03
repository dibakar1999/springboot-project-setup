package com.setupproject.setup.service.impl;

import com.setupproject.setup.service.JwtTokenGeneratorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author Dibakar Chaudhary
 * 02/12/2024
 **/
@Service
public class JwtTokenGeneratorServiceImpl implements JwtTokenGeneratorService {
    private final String SECRET_KEY;

    public JwtTokenGeneratorServiceImpl() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //10 min exp time
                .signWith(this.getKey())
                .compact();
    }

    @Override
    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractClaims(token, Claims::getSubject);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
