package com.db.ar.service.utils;

import br.com.db.rapid_food_api.order.domain.Order;
import br.com.db.rapid_food_api.order.domain.OrderItem;

import java.math.BigDecimal;

public class TotalAmountCalc {
    public static BigDecimal calculate(Order order) {
        return order.getItems().stream().map(OrderItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
