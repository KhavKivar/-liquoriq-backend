package com.khavkivar.liquoriq.controller;

import com.khavkivar.liquoriq.model.Order;
import com.khavkivar.liquoriq.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "Test new config on cloud machine";
    }


    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable long id) {
        return orderService.getOrder(id);
    }
}
