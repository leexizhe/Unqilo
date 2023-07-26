package com.uniqlo.orderservice.service;

import com.uniqlo.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
