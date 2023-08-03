package com.uniqlo.orderservice.controller;

import com.uniqlo.orderservice.dto.OrderRequest;
import com.uniqlo.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CompletableFuture<String> fallbackMethod(Throwable throwable) {
        log.debug("Inventory Service is down!", throwable);
        return CompletableFuture.supplyAsync(() -> "Try again later");
    }
}
