package com.khavkivar.liquoriq.service;

import com.khavkivar.liquoriq.model.Order;
import com.khavkivar.liquoriq.model.Product;
import com.khavkivar.liquoriq.repository.OrderRepository;
import com.khavkivar.liquoriq.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        // If an order is provided with only id, ensure it's managed
        if (product.getOrder() != null) {
            Order provided = product.getOrder();
            if (provided.getId() != null) {
                Order managedOrder = orderRepository.findById(provided.getId())
                        .orElseThrow(() -> new IllegalStateException("Order not found: " + provided.getId()));
                product.setOrder(managedOrder);
                // Keep bidirectional list consistent in memory
                if (managedOrder.getProducts() == null) {
                    managedOrder.setProducts(new ArrayList<>());
                }
                if (!managedOrder.getProducts().contains(product)) {
                    managedOrder.getProducts().add(product);
                }
            } else {
                // If a transient order is provided without id, also keep consistency
                if (provided.getProducts() == null) {
                    provided.setProducts(new ArrayList<>());
                }
                if (!provided.getProducts().contains(product)) {
                    provided.getProducts().add(product);
                }
            }
        }
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalStateException());
    }
}
