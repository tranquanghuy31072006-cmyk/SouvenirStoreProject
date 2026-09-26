package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.configuration.JwtProperties;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.repository.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {
    JwtEncoder jwtEncoder;
    JwtProperties jwtProperties;
    JwtDecoder jwtDecoder;
    InvalidatedTokenRepository invalidatedTokenRepository;

    public String generateToken(UserDetails userDetails) {
        Instant issuedAt = Instant.now();

        Instant expiration = issuedAt.plusSeconds(jwtProperties.getExpiration());

        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User role not found"));

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiresAt(expiration)
                .claim("role", role)
                .id(UUID.randomUUID().toString())
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);

        return jwtEncoder.encode(parameters).getTokenValue();
    }


    public Jwt verifyToken(String token, boolean isRefresh) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            if(invalidatedTokenRepository.existsInvalidatedTokenById(jwt.getId())){
                log.warn("Token with id: {} has been in Black list", jwt.getId());
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return jwt;
        }catch (JwtException e) {
            log.warn("Invalid Token");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
