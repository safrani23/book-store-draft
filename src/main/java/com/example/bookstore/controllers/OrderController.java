package com.example.bookstore.controllers;

import com.example.bookstore.models.Order;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.security.UserDetails;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(
            ProductService productService, OrderService orderService, OrderRepository orderRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order/create")
    public String order(){
        orderService.createOrder();
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByUser(userDetails.getUser());
        model.addAttribute("orders", orderList);
        return "orders/list";
    }

}
