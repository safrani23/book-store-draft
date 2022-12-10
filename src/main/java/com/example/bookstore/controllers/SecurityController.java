package com.example.bookstore.controllers;

import com.example.bookstore.models.User;
import com.example.bookstore.security.UserDetails;
import com.example.bookstore.services.UserService;
import com.example.bookstore.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SecurityController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public SecurityController(
            UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/auth")
    public String authorization(){
        return "security/auth";
    }

    @GetMapping("/registration")
    public String registration(
            @ModelAttribute("user") User user){
        return "security/registration";
    }

    @PostMapping("/registration")
    public String saveUser(
            @ModelAttribute("user")
            @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "security/registration";
        }
        userService.saveUser(user);
        return "redirect:/auth";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        model.addAttribute("userProfile", userService.getUserById(user_id));
        return  "user/userProfile";
    }
}