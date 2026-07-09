package com.db.ar.feign;

import com.db.ar.feign.dtos.UserFeignDto;
import org.apache.coyote.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(url = "http://localhost:8084/api/users", name = "order.user")
public interface UserFeignClient {

    @GetMapping("/{userId}")
    ResponseEntity<UserFeignDto> getById(@PathVariable UUID userId);
}
