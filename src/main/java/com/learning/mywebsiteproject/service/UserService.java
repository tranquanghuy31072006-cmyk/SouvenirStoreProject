package com.learning.mywebsiteproject.service;

import com.learning.mywebsiteproject.dto.request.UserCreationRequest;
import com.learning.mywebsiteproject.dto.response.UserResponse;
import com.learning.mywebsiteproject.entity.User;
import com.learning.mywebsiteproject.mapper.UserMapper;
import com.learning.mywebsiteproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;

//    UserResponse createUser(UserCreationRequest userCreationRequest){
//        User user = userMapper.toUser(userCreationRequest);
//        return userRepository.save(user);
//    }
}
