package com.example.bookstore.util;

import com.example.bookstore.models.User;
import com.example.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    // В данном методе указываем для какой модели предназначен данный валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    // В данном методе мы прописываем правила валидации
    @Override
    public void validate(Object target, Errors errors) {
        User userLogin = (User) target;
        if (userService.findByUsername(userLogin) != null) {
            errors.rejectValue("login", "", "Данный логин уже занят");
        }
    }
}

