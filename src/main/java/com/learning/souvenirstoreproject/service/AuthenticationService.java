package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.configuration.JwtProperties;
import com.learning.souvenirstoreproject.dto.request.*;
import com.learning.souvenirstoreproject.dto.response.IntrospectResponse;
import com.learning.souvenirstoreproject.dto.response.LoginResponse;
import com.learning.souvenirstoreproject.entity.InvalidatedToken;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.repository.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    JwtProperties jwtProperties;
    InvalidatedTokenRepository invalidatedTokenRepository;
    UserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(jwtProperties.getExpiration())
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            jwtService.verifyToken(request.getToken(), false);

            return IntrospectResponse.builder().valid(true).build();
        } catch (Exception e) {
            return IntrospectResponse.builder().valid(false).build();
        }
    }

    public void logout(LogoutRequest request) {
        try {
            Jwt jwt = jwtService.verifyToken(request.getToken(), false);
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwt.getId())
                    .expirationTime(jwt.getExpiresAt())
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("The token is expired or invalid, skipping addition to the blacklist.");
        }
    }

    public LoginResponse refreshToken(RefreshRequest request) {
        Jwt jwt = jwtService.verifyToken(request.getToken(), true);

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwt.getId())
                .expirationTime(jwt.getExpiresAt())
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        String username = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(jwtProperties.getExpiration())
                .build();
    }
}
