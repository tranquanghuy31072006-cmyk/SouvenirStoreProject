package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.request.UserCreationRequest;
import com.learning.souvenirstoreproject.dto.request.UserUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.UserResponse;
import com.learning.souvenirstoreproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    List<UserResponse> toUserResponseList(List<User> users);
}
