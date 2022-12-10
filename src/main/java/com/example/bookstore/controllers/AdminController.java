package com.example.bookstore.controllers;

import com.example.bookstore.models.Order;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;

    private final OrderRepository orderRepository;

    private final OrderService orderService;

    public AdminController(UserService userService, OrderRepository orderRepository, OrderService orderService) {
        this.userService = userService;
        this.orderRepository = orderRepository;
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

    @GetMapping("/admin/orders")
    public String ordersList(Model model){
        model.addAttribute("ordersList", orderRepository.findAll());
        return "admin/orders";
    }
/*
    @PostMapping("/admin/orders")
    public String ordersList(
            @RequestParam(value = "number", required = false) String number, Model model) {
        if (!number.isEmpty()){
            System.out.println("Поиск заказа");
            List<Order> orderList = orderRepository.findOrderByNumber();
            System.out.println(orderList);
            model.addAttribute("ordersList", orderList);
            model.addAttribute("value_number", number);
            return "admin/orders";
        }
        List<Order> orderList = orderRepository.findOrderByNumber();
        System.out.println(orderList);
        model.addAttribute("ordersList", orderList);
        return "admin/orders";
    }
*/
/*
    @PostMapping("/admin/orders")
    public String ordersList(Model model){
        List<Order> orderList = orderRepository.findOrderByNumber();
        model.addAttribute("ordersList", orderList);
        return "admin/orders";
    }
*/
    @GetMapping("/admin/order/{id}")
    public String orderStatus(@PathVariable("id") int id, Model model){
        model.addAttribute("orderId", userService.getUserById(id));
        return "admin/order-details";
    }

    @PostMapping("/admin/order/{id}")
    public String orderStatus(
            @ModelAttribute("orderId") Order order,
            @PathVariable("id") int id){
        orderService.updateOrderStatus(id, order);
        return "redirect:/order-details";
    }
}
