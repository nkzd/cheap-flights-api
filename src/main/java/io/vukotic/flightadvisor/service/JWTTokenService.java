package io.vukotic.flightadvisor.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vukotic.flightadvisor.persistence.repository.UserRepository;
import io.vukotic.flightadvisor.dto.LoginDto;
import io.vukotic.flightadvisor.error.exception.JWTException;
import io.vukotic.flightadvisor.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTTokenService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.signing.key}")
    private String signingKey;

    public String createJwtToken(LoginDto login) {
        var user = userRepository.findUserByUsername(login.getUsername()).orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            var key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
            var token = Jwts.builder()
                    .setClaims(Map.of("username", user.getUsername()))
                    .signWith(key)
                    .compact();
            return "Bearer "+token;
        } else {
            throw new UserNotFoundException();
        }
    }

    public UsernamePasswordAuthenticationToken parseJwtToken(String token) {
        byte[] secretBytes = signingKey.getBytes(StandardCharsets.UTF_8);
        try {
            var jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(secretBytes)
                    .build()
                    .parseClaimsJws(token);
            var username = String.valueOf(jwsClaims.getBody().get("username"));
            var user = userRepository.findUserByUsername(username).orElseThrow(JWTException::new);
            var authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }catch(JwtException jwtException){
            throw new JWTException();
        }
    }

}
