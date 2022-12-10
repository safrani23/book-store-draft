package com.example.bookstore.repositories;

import com.example.bookstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1)))", nativeQuery = true)
    List<Product> filterByProductTitleIgnoreCase(String title);

    // Критерий поиска товара: Цена "от" - "до"
    @Query(value = "select * from t_product where (price >= ?1 and price <= ?2)", nativeQuery = true)
    List<Product> filterProductByPriceFromTo(float from, float to);

    @Query(value = "select * from t_product where (price >= ?1)", nativeQuery = true)
    List<Product> filterProductByPriceFrom(float from);

    @Query(value = "select * from t_product where (price <= ?1)", nativeQuery = true)
    List<Product> filterProductByPriceTo(float to);


    // Комбинации запросов Наименование + Цена "от" - "до"
    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2)", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFrom(String title, float from);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price <= ?2)", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceTo(String title, float to);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2) and (price <= ?3)", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFromAndPriceTo(String title, float from, float to);


    // Комбинации Наименование + Цена "от" - "до" + Критерий для сортировки asc

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2) order by price", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFromAsc(String title, float from);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price <= ?2) order by price", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceToAsc(String title, float to);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2) and (price <= ?3) order by price", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFromAndPriceToAsc(String title, float from, float to);

    // Комбинации Наименование + Цена "от" - "до" + Критерий для сортировки desc

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2) order by price desc", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFromDesc(String title, float from);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price <= ?2) order by price desc", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceToDesc(String title, float to);

    @Query(value = "select * from t_product where ((lower(title) LIKE CONCAT('%',?1,'%')) or (lower(title) LIKE CONCAT(?1,'%')) or (lower(title) LIKE CONCAT('%',?1))) and (price >= ?2) and (price <= ?3) order by price desc", nativeQuery = true)
    List<Product> filterProductByTitleIgnoreCaseAndByPriceFromAndPriceToDesc(String title, float from, float to);

    // ТОЛЬКО СОРТИРОВКА ПО ЦЕНЕ
    @Query(value = "select * from t_product order by price", nativeQuery = true)
    List<Product> findProductByPriceAsc();

    @Query(value = "select * from t_product order by price desc", nativeQuery = true)
    List<Product> findProductByPriceDesc();

    // Критерий поиска по категории

    // Belletristic

    // # ТОЛЬКО КАТЕГОРИЯ 1
    @Query(value = "select * from t_product where category_id=?1", nativeQuery = true)
    List<Product> findProductByGenreBelletristic(int category);

    // # ТОЛЬКО КАТЕГОРИЯ 1 + ASC
    @Query(value = "select * from t_product where category_id=?1 order by price", nativeQuery = true)
    List<Product> findProductByGenreBelletristicPriceAsc(int category);

    // # ТОЛЬКО КАТЕГОРИЯ 1 + DESC
    @Query(value = "select * from t_product where category_id=?1 order by price desc", nativeQuery = true)
    List<Product> findProductByGenreBelletristicPriceDesc(int category);

    // # ТОЛЬКО КАТЕГОРИЯ 1 + TITLE

    // # ТОЛЬКО КАТЕГОРИЯ 1 + ЦЕНА ОТ

    // # ТОЛЬКО КАТЕГОРИЯ 1 + ЦЕНА ДО

    // # ТОЛЬКО КАТЕГОРИЯ 1 + ЦЕНА ОТ - ДО

    //

    @Query(value = "select * from t_product where category_id=?1 order by price", nativeQuery = true)
    List<Product> findProductByGenreBusinessPriceAsc(int category);

    @Query(value = "select * from t_product where category_id=?1 order by price desc", nativeQuery = true)
    List<Product> findProductByGenreBusinessPriceDesc(int category);

    @Query(value = "select * from t_product where category_id=?1", nativeQuery = true)
    List<Product> findProductByGenreBusiness(int category);

    @Query(value = "select * from t_product where category_id=?1 order by price", nativeQuery = true)
    List<Product> findProductByGenrePsychologyPriceAsc(int category);

    @Query(value = "select * from t_product where category_id=?1 order by price desc", nativeQuery = true)
    List<Product> findProductByGenrePsychologyPriceDesc(int category);

    @Query(value = "select * from t_product where category_id=?1", nativeQuery = true)
    List<Product> findProductByGenrePsychology(int category);

    @Query(value = "select * from t_product where category_id=?1 order by price", nativeQuery = true)
    List<Product> findProductByGenrePhilosophyAndReligionPriceAsc(int category);

    @Query(value = "select * from t_product where category_id=?1 order by price desc", nativeQuery = true)
    List<Product> findProductByGenrePhilosophyAndReligionPriceDesc(int category);

    @Query(value = "select * from t_product where category_id=?1", nativeQuery = true)
    List<Product> findProductByGenrePhilosophyAndReligion(int category);



}
