package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.RegisterRequest;
import com.learning.souvenirstoreproject.dto.request.UserPasswordUpdateRequest;
import com.learning.souvenirstoreproject.dto.request.UserCreationRequest;
import com.learning.souvenirstoreproject.dto.request.UserUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.UserResponse;
import com.learning.souvenirstoreproject.entity.Role;
import com.learning.souvenirstoreproject.entity.User;
import com.learning.souvenirstoreproject.enums.UserStatus;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.UserMapper;
import com.learning.souvenirstoreproject.repository.RoleRepository;
import com.learning.souvenirstoreproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserDetailsService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    //createUser
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsUserByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (userRepository.existsByEmail(userCreationRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        Role role = roleRepository.findById("CUSTOMER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    //getUserById
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    //updateUserById
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, userUpdateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //getAllUsers
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    //deleteUserById
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //getMyInfo
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }


    //changePassword
    public void changePassword(UserPasswordUpdateRequest userPasswordUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userPasswordUpdateRequest.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }

        user.setPassword(passwordEncoder.encode(userPasswordUpdateRequest.getNewPassword()));

        userRepository.save(user);
    }

    //activateUser
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse activateUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new AppException(ErrorCode.USER_ALREADY_BLOCK);
        }
        user.setStatus(UserStatus.ACTIVE);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //deactivateUser
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse deactivateUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getStatus() == UserStatus.ACTIVE)
            throw new AppException(ErrorCode.USER_ALREADY_ACTIVE);

        user.setStatus(UserStatus.BLOCKED);

        return userMapper.toUserResponse(userRepository.save(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().getName())
                .disabled(user.getStatus() != UserStatus.ACTIVE)
                .build();
    }

    //register
    public UserResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsUserByUsername(registerRequest.getUsername()))
            throw new AppException(ErrorCode.USERNAME_EXISTED);

        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        if (userRepository.existsUserByPhone(registerRequest.getPhone()))
            throw new AppException(ErrorCode.PHONE_EXISTED);

        Role role = roleRepository.findById("CUSTOMER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));


        User user = userMapper.toUser(registerRequest);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }
}
