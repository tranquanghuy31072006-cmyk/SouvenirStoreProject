package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.UserCreationRequest;
import com.learning.souvenirstoreproject.entity.User;
import com.learning.souvenirstoreproject.mapper.UserMapper;
import com.learning.souvenirstoreproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grocery-store")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserRepository userRepository;
    UserMapper userMapper;

    //createUser
    User createUser(UserCreationRequest userCreationRequest){
        User user = userMapper.toUser(userCreationRequest);
        return userRepository.save(user);
    }
}
