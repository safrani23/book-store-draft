package com.example.bookstore.controllers;

import com.example.bookstore.models.Basket;
import com.example.bookstore.models.Product;
import com.example.bookstore.repositories.BasketRepository;
import com.example.bookstore.security.UserDetails;
import com.example.bookstore.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BasketController {

    private final BasketRepository basketRepository;

    private final ProductService productService;

    public BasketController(
            BasketRepository basketRepository,
            ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    @GetMapping("/basket") // получение корзины авторизованного пользователя
    public String basket(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Basket> basketList = basketRepository.findByUserId(user_id);
        List<Product> productList = new ArrayList<>();
        for (Basket basket: basketList) {
            productList.add(productService.getProductById(basket.getProductId()));
        }
        float price = 0;
        for (Product product: productList) {
            price += product.getPrice();
        }
        model.addAttribute("price", price);
        model.addAttribute("basket_product", productList);
        return "basket/basket";
    }

    @GetMapping("/basket/{id}/add") // добавление товара в корзину
    public String basketAddProduct(
            @PathVariable("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        Product product = productService.getProductById(id);
        Basket basket = new Basket(user_id,product.getId());
        basketRepository.save(basket);
        return "redirect:/catalog";
    }

    @GetMapping("/basket/{id}/delete")
    public String basketDeleteProduct(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        basketRepository.deleteItemByProductId(id);
        return "redirect:/basket";
    }
}
