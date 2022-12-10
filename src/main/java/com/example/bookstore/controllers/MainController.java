package com.example.bookstore.controllers;

import com.example.bookstore.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getUser().getRole();
        if (role.equals("ROLE_ADMIN")) {
            return "admin/admin";
        }
        else if (role.equals("ROLE_USER")) {
            return "user/user";
        }
        return "index";
    }

}