package com.uniqlo.orderservice.service;

import com.uniqlo.orderservice.dto.InventoryResponse;
import com.uniqlo.orderservice.dto.OrderLinesItemsDto;
import com.uniqlo.orderservice.dto.OrderRequest;
import com.uniqlo.orderservice.model.Order;
import com.uniqlo.orderservice.model.OrderLineItems;
import com.uniqlo.orderservice.repository.OrderRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private WebClient.Builder webClientBuilder;


    public String placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLinesItemsDtoList().stream()
                .map(this::mapFromDto)
                .collect(Collectors.toList());

        List<String> skuCodes =
                orderLineItemsList.stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        InventoryResponse[] inventoryResponseArray = webClientBuilder
                .build()
                .get()
                .uri(
                        "http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = false;
        if (inventoryResponseArray != null) {
            allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getInStock);
        }

        if (allProductsInStock) {
            Order order = Order.builder()
                    .orderLineItemsList(orderLineItemsList)
                    .orderNumber(UUID.randomUUID().toString())
                    .build();

            log.info("Order Number : {}", order.getOrderNumber());
            orderRepository.save(order);
            log.info("Order is saved!");
            return "Order Placed Successfully!";
        } else {
            throw new IllegalArgumentException("Product is not in stock, try again tomorrow!");
        }
    }

    private OrderLineItems mapFromDto(OrderLinesItemsDto orderLinesItemsDto) {
        return OrderLineItems.builder()
                .price(orderLinesItemsDto.getPrice())
                .quantity(orderLinesItemsDto.getQuantity())
                .skuCode(orderLinesItemsDto.getSkuCode())
                .build();
    }
}
