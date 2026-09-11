package com.learning.mywebsiteproject.controller;

import com.learning.mywebsiteproject.dto.request.UserCreationRequest;
import com.learning.mywebsiteproject.entity.User;
import com.learning.mywebsiteproject.mapper.UserMapper;
import com.learning.mywebsiteproject.repository.UserRepository;
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
