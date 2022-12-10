package com.example.bookstore.services;

import com.example.bookstore.classifiers.Status;
import com.example.bookstore.models.Basket;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.OrderDetails;
import com.example.bookstore.models.Product;
import com.example.bookstore.repositories.BasketRepository;
import com.example.bookstore.repositories.OrderDetailRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final ProductService productService;

    private final OrderDetailRepository orderDetailRepository;

    //@Autowired
    public OrderService(OrderRepository orderRepository, BasketRepository basketRepository, ProductService productService, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    public void updateOrderStatus(int id, Order order) {
        order.setId(id);
        order.setDateTime(LocalDateTime.now());
        orderRepository.save(order); // обновить продукт в репозитории
    }

    @Transactional
    public void createOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Basket> basketList = basketRepository.findByUserId(user_id);

        String uuid = UUID.randomUUID().toString();
        var order = new Order(uuid, userDetails.getUser(), Status.CREATED);
        orderRepository.save(order);

        for (Basket basket : basketList) {
            basketRepository.delete(basket);
            var detail = new OrderDetails();
            detail.setOrder(order);
            detail.setCount(1);
            var product = productService.getProductById(basket.getProductId());
            detail.setPrice(product.getPrice());
            detail.setProduct(product);
            orderDetailRepository.save(detail);
        }
    }

}
