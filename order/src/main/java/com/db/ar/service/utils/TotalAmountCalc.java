package com.db.ar.service.utils;

import com.db.ar.domain.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class TotalAmountCalc {

    public static BigDecimal calculate(List<OrderItem> items) {
        return items.stream().map(OrderItem::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
