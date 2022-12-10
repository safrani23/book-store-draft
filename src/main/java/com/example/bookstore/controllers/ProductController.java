package com.example.bookstore.controllers;

import com.example.bookstore.models.Image;
import com.example.bookstore.models.Product;
import com.example.bookstore.repositories.CategoryRepository;
import com.example.bookstore.security.UserDetails;
import com.example.bookstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ProductController {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductController(
            ProductService productService,
            CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/catalog")
    public String productList(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return "product/list";
    }

    @GetMapping("/catalog-list")
    public String productListForAll(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return "list";
    }

    @GetMapping("/product/add")
    public String productAdd(Model model) {
        model.addAttribute("productAdd", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/add";
    }

    @PostMapping("/product/add")
    public String productAdd(
            @ModelAttribute("productAdd")
            @Valid Product product, BindingResult bindingResult,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/add";
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString(); // уникальное имя файла
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImage(image);
        }
        productService.saveProduct(product);
        return "redirect:/catalog";
    }

    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("productEdit", productService.getProductById(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/edit";
    }

    @PostMapping("/product/{id}/edit")
    public String productEdit(@ModelAttribute("productEdit")
                              @Valid Product product, BindingResult bindingResult,
                              @PathVariable("id") int id,
                              @RequestParam("file") MultipartFile file) throws IOException {
        productService.updateProduct(id, product);
        if (bindingResult.hasErrors()) {
            return "redirect:/product/{id}/edit";
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString(); // уникальное имя файла
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImage(image);
        }
        return "redirect:/catalog";
    }

    @GetMapping("/product/{id}/delete")
    public String productDelete(@PathVariable("id") int id) {
        productService.removeProduct(id);
        return "redirect:/catalog";
    }

    @GetMapping("/catalog/item/{id}")
    public String getBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("item", productService.getProductById(id));
        return "product/get";
    }
}
