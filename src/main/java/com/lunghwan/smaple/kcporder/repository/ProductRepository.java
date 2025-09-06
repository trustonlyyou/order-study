package com.lunghwan.smaple.kcporder.repository;

import com.lunghwan.smaple.kcporder.constant.Category;
import com.lunghwan.smaple.kcporder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);

    List<Product> findProductByCategory(Category category);

}
