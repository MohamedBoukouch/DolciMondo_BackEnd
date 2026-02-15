package com.example.DolciMondo_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DolciMondo_backend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}