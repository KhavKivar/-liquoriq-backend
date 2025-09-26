package com.khavkivar.liquoriq.repository;

import com.khavkivar.liquoriq.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "products")
    List<Order> findAll();
}
