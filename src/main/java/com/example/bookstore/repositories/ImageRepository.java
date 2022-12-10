package com.example.bookstore.repositories;

import com.example.bookstore.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Integer> {

}
