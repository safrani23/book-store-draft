package com.example.bookstore.repositories;

import com.example.bookstore.models.Order;
import com.example.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    @Query(value = "select * from t_orders where ((lower(number) LIKE '%?1%') or (lower(number) LIKE '?1%') or (lower(number) LIKE '%?1'))", nativeQuery = true)
    List<Order> filterOrderByNumberIgnoreCase(String number);


    @Query(value = "select distinct number, status from t_orders", nativeQuery = true)
    List<Order> findOrderByNumber();

}