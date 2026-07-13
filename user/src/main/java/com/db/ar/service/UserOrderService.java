package com.db.ar.service;

import com.db.ar.mapper.UserOrderMapper;
import com.db.ar.messaging.representation.OrderEventRepresentation;
import com.db.ar.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final UserOrderMapper userOrderMapper;

    public void saveUserOrder(OrderEventRepresentation representation) {
        var userOrder = userOrderMapper.toEntity(representation);
        userOrderRepository.save(userOrder);
    }
}
