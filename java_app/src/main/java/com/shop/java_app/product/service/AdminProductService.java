package com.shop.java_app.product.service;

import com.shop.java_app.common.exception.ErrorCode;
import com.shop.java_app.common.exception.NotFoundException;
import com.shop.java_app.product.dto.ProductRequest;
import com.shop.java_app.product.dto.ProductResponse;
import com.shop.java_app.product.entity.Product;
import com.shop.java_app.product.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final AdminProductRepository adminProductRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        final Product product = adminProductRepository.save(request.toEntity());
        return product.toResponse();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return adminProductRepository.findAll().stream()
                .map(Product::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        final Product product = adminProductRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));
        product.updateProduct(request);
        return product.toResponse();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        final Product product = adminProductRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));
        adminProductRepository.delete(product);
    }
}
