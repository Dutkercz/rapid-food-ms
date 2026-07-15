package com.db.ar.service;

import com.db.ar.domain.User;
import com.db.ar.dto.CreateUserRequest;
import com.db.ar.dto.UpdateUserRequest;
import com.db.ar.dto.UserResponse;
import com.db.ar.exception.EmailAlreadyExistsException;
import com.db.ar.exception.UserNotFoundException;
import com.db.ar.mapper.UserMapper;
import com.db.ar.messaging.representation.payment.PaymentEventRep;
import com.db.ar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmailEqualsIgnoreCase(request.email().trim())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        String passwordHash = encoder.encode(request.password());

        User user = userMapper.toEntity(request, passwordHash);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = findUser(id);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = findUser(id);
        if (request.email() != null && userRepository.existsByEmailEqualsIgnoreCaseAndIdNot(request.email(), id)) {
            throw new EmailAlreadyExistsException(request.email());
        }
        String passwordHash = null;
        if (request.password() != null) {
            passwordHash = encoder.encode(request.password());
        }
        userMapper.updateEntity(user, request, passwordHash);
        user.setUpdatedAt(LocalDateTime.now());

        /// publicar evento de update de usuario ou não

        return userMapper.toResponse(user);
    }

    ///================///
    ///Private Methods///
    ///================///

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
