package com.example.bookstore.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    @NotNull(message = "Поле должно быть заполнено")
    private int code;

    @Column(name = "title", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String title;

    @Column(name = "author", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String author;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String description;

    @Column(name = "publishing_house", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String publishing_house;

    @Column(name = "year_of_publishing", nullable = false)
    @NotNull(message = "Поле должно быть заполнено")
    private int year_of_publishing;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 1, message = "Цена должна быть больше нуля")
    private float price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList = new ArrayList<>();


    public Product() {
    }

    public void addImage(Image image){
        image.setProduct(this);
        imageList.add(image);
    }

    @ManyToOne(optional = false)
    private Category category;

    @ManyToMany()
    @JoinTable(name = "t_basket", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

    private LocalDateTime dateTime;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public void setPublishing_house(String publishing_house) {
        this.publishing_house = publishing_house;
    }

    public int getYear_of_publishing() {
        return year_of_publishing;
    }

    public void setYear_of_publishing(int year_of_publishing) {
        this.year_of_publishing = year_of_publishing;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
