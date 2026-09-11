package com.learning.mywebsiteproject.mapper;

import com.learning.mywebsiteproject.dto.request.UserCreationRequest;
import com.learning.mywebsiteproject.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
}
