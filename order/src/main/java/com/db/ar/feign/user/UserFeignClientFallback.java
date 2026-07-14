package com.db.ar.feign.user;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public ResponseEntity<UserFeignDto> getById(Long userId) {
                var userFallback = new UserFeignDto(userId, "indisponível", "indisponível",
                        null, null);
                return ResponseEntity.ok(userFallback);
            }
        };
    }
}
