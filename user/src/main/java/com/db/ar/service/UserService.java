package com.db.ar.service;

import com.db.ar.domain.User;
import com.db.ar.dto.CreateUserRequest;
import com.db.ar.dto.UpdateUserRequest;
import com.db.ar.dto.UserResponse;
import com.db.ar.exception.EmailAlreadyExistsException;
import com.db.ar.exception.UserNotFoundException;
import com.db.ar.mapper.UserMapper;
import com.db.ar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmailEqualsIgnoreCase(request.email().trim())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        String passwordHash = passwordEncoder.encode(request.password());
        User user = userMapper.toEntity(request, passwordHash);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (request.email() != null && userRepository.existsByEmailEqualsIgnoreCaseAndIdNot(request.email(), id)) {
            throw new EmailAlreadyExistsException(request.email());
        }
        String passwordHash = null;
        if (request.password() != null) {
            passwordHash = passwordEncoder.encode(request.password());
        }
        user.update(
                request.name(),
                request.email(),
                passwordHash,
                request.active());
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
