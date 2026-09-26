package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.*;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.IntrospectResponse;
import com.learning.souvenirstoreproject.dto.response.LoginResponse;
import com.learning.souvenirstoreproject.dto.response.UserResponse;
import com.learning.souvenirstoreproject.service.AuthenticationService;
import com.learning.souvenirstoreproject.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.registerUser(registerRequest))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<LoginResponse>builder()
                .result(authenticationService.login(loginRequest))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest logoutRequest) {
        authenticationService.logout(logoutRequest);

        return ApiResponse.<String>builder()
                .message("Successfully logged out")
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse>  introspect(@RequestBody IntrospectRequest introspectRequest) {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(introspectRequest))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ApiResponse.<LoginResponse>builder()
                .result(authenticationService.refreshToken(refreshRequest))
                .build();
    }
}
