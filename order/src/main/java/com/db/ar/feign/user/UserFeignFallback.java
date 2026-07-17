package com.db.ar.feign.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallback implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public ResponseEntity<UserFeignDto> getById(Long userId) {
                throw new EntityNotFoundException("User not found or unavailable at the moment.");
            }
        };
    }
}
