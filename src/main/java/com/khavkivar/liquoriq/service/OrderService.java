package com.khavkivar.liquoriq.service;

import com.khavkivar.liquoriq.model.Order;
import com.khavkivar.liquoriq.model.Product;
import com.khavkivar.liquoriq.repository.OrderRepository;
import com.khavkivar.liquoriq.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        // Ensure date is set
        if (order.getDate() == null) {
            order.setDate(LocalDateTime.now());
        }

        // Attach products safely: if a product has an id, load the managed entity
        List<Product> attachedProducts = new ArrayList<>();
        if (order.getProducts() != null) {
            for (Product incoming : order.getProducts()) {
                if (incoming == null) continue;
                if (incoming.getId() != null) {
                    Product managed = productRepository.findById(incoming.getId())
                            .orElseThrow(() -> new IllegalStateException("Product not found: " + incoming.getId()));
                    managed.setOrder(order);
                    attachedProducts.add(managed);
                } else {
                    incoming.setOrder(order);
                    attachedProducts.add(incoming);
                }
            }
        }
        if (!attachedProducts.isEmpty()) {
            order.setProducts(attachedProducts);
        }

        return orderRepository.save(order);
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalStateException());
    }
}
