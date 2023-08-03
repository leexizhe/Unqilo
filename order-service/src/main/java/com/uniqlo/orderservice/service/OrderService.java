package com.uniqlo.orderservice.service;

import com.uniqlo.orderservice.dto.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);
}
