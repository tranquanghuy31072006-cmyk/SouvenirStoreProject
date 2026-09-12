package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.UserCreationRequest;
import com.learning.souvenirstoreproject.dto.request.UserUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.UserResponse;
import com.learning.souvenirstoreproject.entity.Role;
import com.learning.souvenirstoreproject.entity.User;
import com.learning.souvenirstoreproject.mapper.UserMapper;
import com.learning.souvenirstoreproject.repository.RoleRepository;
import com.learning.souvenirstoreproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;

    //createUser
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        User user = userMapper.toUser(userCreationRequest);
        Role role = roleRepository.findById("CUSTOMER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    //getUserById
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(userRepository.findUsersById(id));
    }

    //updateUserById
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user,userUpdateRequest);

        return userMapper.toUserResponse(user);
    }
    //getAllUsers
    public List<UserResponse> getAllUsers(){
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    //deleteUserById
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //getMyInfo
}
