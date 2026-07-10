package com.db.ar.mapper;

import com.db.ar.domain.User;
import com.db.ar.dto.CreateUserRequest;
import com.db.ar.dto.UpdateUserRequest;
import com.db.ar.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash",  source = "passwordHash")
    User toEntity(CreateUserRequest request, String passwordHash);

    UserResponse toResponse(User user);

    void updateEntity(@MappingTarget User user, UpdateUserRequest request, String passwordHash);
}
