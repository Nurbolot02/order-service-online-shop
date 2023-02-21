package com.ntg.orderserviceonlineshop.controller;

import com.ntg.orderserviceonlineshop.dto.OrderRequest;
import com.ntg.orderserviceonlineshop.exception.ItemNotFoundException;
import com.ntg.orderserviceonlineshop.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class orderController {
    private final OrderService orderService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderRequest> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
//    public CompletableFuture<String> createOrder(@Validated @RequestBody OrderRequest orderRequest) {
    public String createOrder(@Validated @RequestBody OrderRequest orderRequest) {
//        return CompletableFuture.supplyAsync(() -> orderService.save(orderRequest));
        return orderService.save(orderRequest);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        if (runtimeException.getClass().equals(ItemNotFoundException.class)) {
            return CompletableFuture.supplyAsync(() -> runtimeException.getMessage());
        }
        return CompletableFuture.supplyAsync(() -> "Oops! something went wrong try again later");
    }
}
