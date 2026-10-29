package com.newcld.fitlog.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.newcld.fitlog.common.UnauthorizedException;

@Service 
public class AuthService {
    private final AuthProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(AuthProperties properties, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.properties = properties;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public TokenResponse login(LoginRequest request) {
        boolean usernameMatches = properties.username().equals(request.username());
        boolean passwordMatches = passwordEncoder.matches(request.password(), properties.passwordHash());
        if(!usernameMatches || !passwordMatches) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않아요.");
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plus(properties.tokenValidityDays(), ChronoUnit.DAYS);


        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("geunjeokgeum")
        .subject(properties.username())
        .issuedAt(now)
        .expiresAt(expiresAt)
        .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new TokenResponse(token, "Bearer", expiresAt);
    }
}
