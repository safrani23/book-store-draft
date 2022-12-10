package com.example.bookstore.controllers;

import com.example.bookstore.classifiers.Status;
import com.example.bookstore.models.Basket;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.Product;
import com.example.bookstore.repositories.BasketRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.security.UserDetails;
import com.example.bookstore.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final ProductService productService;

    public OrderController(
            OrderRepository orderRepository,
            BasketRepository basketRepository,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    @GetMapping("/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Basket> basketList = basketRepository.findByUserId(user_id);
        List<Product> productsList = new ArrayList<>();
        // Получаем продукты из корзины по id
        for (Basket basket: basketList) {
            productsList.add(productService.getProductById(basket.getProductId()));
        }
        float price = 0;
        for (Product product: productsList){
            price += product.getPrice();
        }

        String uuid = UUID.randomUUID().toString();
        for (Product product: productsList){
            Order newOrder = new Order(uuid, product, userDetails.getUser(), 1, product.getPrice(), Status.CREATED);
            orderRepository.save(newOrder);
            basketRepository.deleteItemByProductId(product.getId());
        }
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
