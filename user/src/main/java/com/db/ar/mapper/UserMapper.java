package com.db.ar.mapper;


import com.db.ar.domain.User;
import com.db.ar.dto.CreateUserRequest;
import com.db.ar.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request, String passwordHash) {
        return new User(request.name(), request.email(), passwordHash);
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getActive(), user.getCreatedAt());
    }
}
