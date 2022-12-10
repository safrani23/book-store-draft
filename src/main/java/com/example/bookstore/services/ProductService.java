package com.example.bookstore.services;

import com.example.bookstore.models.Image;
import com.example.bookstore.models.Product;
import com.example.bookstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductList(){
        return productRepository.findAll(); // получить все продукты из репозитория и положить их в список
    }

    public Product getProductById(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null); // получить продукт по id
    }

    @Transactional
    public void saveProduct(Product product){
        productRepository.save(product); // сохранить продукт в репозиторий
    }

    @Transactional
    public void updateProduct(int id, Product product){
        product.setId(id);
        product.setDateTime(LocalDateTime.now());
        product.addImage(new Image());
        productRepository.save(product); // обновить продукт в репозитории
    }

    @Transactional
    public void removeProduct(int id){
        productRepository.deleteById(id); // удалить продукт из репозитория
    }
}
