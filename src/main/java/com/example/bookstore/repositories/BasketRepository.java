package com.example.bookstore.repositories;

import com.example.bookstore.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BasketRepository extends JpaRepository<Basket,Integer> {

    List<Basket> findByUserId(int id); // получение корзины по id пользователя

    void deleteItemByProductId(int id);

}
