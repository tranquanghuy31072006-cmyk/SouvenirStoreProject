package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.mapper.UserMapper;
import com.learning.souvenirstoreproject.repository.UserRepository;
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
