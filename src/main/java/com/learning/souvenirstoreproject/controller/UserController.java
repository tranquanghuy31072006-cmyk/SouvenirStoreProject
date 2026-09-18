package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.UserPasswordUpdateRequest;
import com.learning.souvenirstoreproject.dto.request.UserCreationRequest;
import com.learning.souvenirstoreproject.dto.request.UserUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.UserResponse;
import com.learning.souvenirstoreproject.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    //createUser
    @PostMapping("/register")
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userCreationRequest))
                .build();
    }

    //getUserById
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable Long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    //updateUser
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, userUpdateRequest))
                .build();
    }

    //getAllUsers
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    //deleteUser
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("User has been deleted successfully")
                .build();
    }

    //changePassword
    @PutMapping("/change-password/{userId}")
    ApiResponse<String> changePassword(@PathVariable Long userId, @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest){
        userService.changePassword(userId, userPasswordUpdateRequest);
        return ApiResponse.<String>builder()
                .message("Password Updated")
                .build();
    }

    //deactivateUserStatus
    @PutMapping("/deactivate-user-status/{userId}")
    ApiResponse<UserResponse> deactivateUserStatus(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.deactivateUserStatus(userId))
                .build();
    }
}
