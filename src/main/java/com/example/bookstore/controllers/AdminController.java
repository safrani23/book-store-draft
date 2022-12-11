package com.example.bookstore.controllers;

import com.example.bookstore.models.Order;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.OrderDetailRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final OrderService orderService;

    public AdminController(
            UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            OrderService orderService) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public String userList(Model model){
        model.addAttribute("userList", userService.getUserList());
        return "admin/users";
    }

    @GetMapping("/user/{id}")
    public String userEdit(@PathVariable("id") int id, Model model){
        model.addAttribute("userEdit", userService.getUserById(id));
        return "admin/profile";
    }

    @PostMapping("/user/{id}")
    public String userEdit(
            @ModelAttribute("userEdit") User user,
            @PathVariable("id") int id){
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/orders")
    public String ordersList(Model model){
        model.addAttribute("ordersList", orderRepository.findAll());
        return "admin/orders";
    }

    @GetMapping("/order/{id}")
    public String orderStatus(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("orderDetails", orderDetailRepository.getReferenceById(id));
        return "admin/order-details";
    }

    @PostMapping("/order/{id}")
    public String orderStatus(
            @ModelAttribute("order") Order order,
            @PathVariable("id") int id){
        orderService.updateOrderStatus(id, order);
        return "redirect:/orders";
    }

    @GetMapping("/order/search")
    public String orderStatus(
            @RequestParam(value = "number", required = false, defaultValue = "") String number, Model model){
        List<Order> orderList = orderRepository.findByOrderNumber(number);
        model.addAttribute("searchOrder", orderList);
        model.addAttribute("value_number", number);
        return "admin/orders";
    }


}
