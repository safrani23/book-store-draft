package com.example.bookstore.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    @ManyToOne(optional = false)
    private User user;

    private LocalDateTime dateTime;

    private Status status;

    public Order() {
    }

    public Order(String number, User user, Status status) {
        this.number = number;
        this.user = user;
        this.status = status;
    }

    public Order(int id, LocalDateTime dateTime, Status status) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
    }

    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
