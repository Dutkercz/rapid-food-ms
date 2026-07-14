package com.db.ar.service;

import com.db.ar.feign.user.UserFeignClient;
import com.db.ar.feign.user.UserFeignDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserIntegrationService {

    private final UserFeignClient userFeignClient;

    @Cacheable(value = "users", key = "#userId", unless = "#result == null")
    public UserFeignDto findeUserById(Long userId) {
        try {
            var response = userFeignClient.getById(userId);
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }catch (Exception e) {
            log.error("Erro ao buscar usuario {}, usando fallback do Feign", userId, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User unavailable");
        }

    }
}
