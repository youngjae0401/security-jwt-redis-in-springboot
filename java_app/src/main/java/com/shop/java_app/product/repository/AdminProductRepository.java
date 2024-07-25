package com.shop.java_app.product.repository;

import com.shop.java_app.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProductRepository extends JpaRepository<Product, Long> {
}
