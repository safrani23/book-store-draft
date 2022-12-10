package com.example.bookstore.services;

import com.example.bookstore.models.User;
import com.example.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(User userLogin){
        Optional<User> user_db = userRepository.findByUsername(userLogin.getUsername());
        return user_db.orElse(null);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        Optional<User> product_db = userRepository.findById(id);
        return product_db.orElse(null);
    }

    @Transactional
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(int id, User user){
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(int id, User user){
        user.setId(id);
        user.setRole(user.getRole());
        userRepository.save(user);
    }
}
