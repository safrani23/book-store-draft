package com.example.bookstore.services;

import com.example.bookstore.models.Order;
import com.example.bookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void updateOrderStatus(int id, Order order){
        order.setId(id);
        order.setDateTime(LocalDateTime.now());
        orderRepository.save(order); // обновить продукт в репозитории
    }
}
