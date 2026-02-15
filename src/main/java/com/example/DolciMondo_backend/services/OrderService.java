package com.example.DolciMondo_backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DolciMondo_backend.dtos.OrderRequest;
import com.example.DolciMondo_backend.models.Order;
import com.example.DolciMondo_backend.models.OrderItem;
import com.example.DolciMondo_backend.models.Product;
import com.example.DolciMondo_backend.models.enums.OrderStatus;
import com.example.DolciMondo_backend.repository.OrderRepository;
import com.example.DolciMondo_backend.repository.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderRequest request) {

        Order order = new Order();
        order.setClientFullName(request.getClientFullName());
        order.setClientPhone(request.getClientPhone());
        order.setClientAddress(request.getClientAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getNewPrice());

            total += product.getNewPrice() * itemRequest.getQuantity();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        order = orderRepository.save(order);

        // Generate reference
        String reference = generateReference(order.getId());
        order.setReference(reference);

        return orderRepository.save(order);
    }

    private String generateReference(Long id) {
        int year = LocalDate.now().getYear();
        return "ORD-" + year + "-" + String.format("%03d", id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
