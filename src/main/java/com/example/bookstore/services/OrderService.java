package com.example.bookstore.services;

import com.example.bookstore.models.Status;
import com.example.bookstore.models.*;
import com.example.bookstore.repositories.BasketRepository;
import com.example.bookstore.repositories.OrderDetailRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public Order getOrderById(int id){
        Optional<Order> order_db = orderRepository.findById(id);
        return order_db.orElse(null);
    }

    @Transactional
    public void updateOrderStatus(int id, Order order){
        order.setId(id);
        order.setDateTime(LocalDateTime.now());
        orderRepository.save(order);
    }
}
