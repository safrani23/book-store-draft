package com.example.bookstore.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "t_user")
public class User{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty
    @Size(min=3, max=16, message = "Имя пользователя должно содержать не менее 3 символов и не более 16")
    private String username;

    @Column(name="email")
    @NotEmpty
    @Pattern(regexp = "^([a-z\\d_-]+\\.)*[a-z\\d_-]+@[a-z\\d_-]+(\\.[a-z\\d_-]+)*\\.[a-z]{2,6}$", message = "Ошибка ввода, пример корректной почты: test@mail.ru"
    )
    private String email;

    @Column(name="phone_number")
    @NotEmpty
    @Pattern(regexp = "^((\\+7|7|8)+(\\d){10})$", message = "Ошибка ввода, пример корректного номера: +7/7/8 777 777 77 77"
    )
    private String phone_number;

    @Column(name = "password")
    @NotEmpty
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, спец символ, букву в верхнем регистре и в нижнем регистре"
    )
    private String password;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
