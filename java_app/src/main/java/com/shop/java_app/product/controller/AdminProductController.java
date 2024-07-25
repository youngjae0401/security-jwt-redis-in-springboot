package com.shop.java_app.product.controller;

import com.shop.java_app.common.dto.ResponseDto;
import com.shop.java_app.product.dto.ProductRequest;
import com.shop.java_app.product.dto.ProductResponse;
import com.shop.java_app.product.service.AdminProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final AdminProductService adminProductService;

    public AdminProductController(final AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping
    public ResponseDto<ProductResponse> createProduct(@RequestBody final ProductRequest request) {
        return ResponseDto.success(adminProductService.createProduct(request));
    }

    @GetMapping
    public ResponseDto<List<ProductResponse>> getProducts() {
        return ResponseDto.success(adminProductService.getProducts());
    }

    @PutMapping("/{productId}")
    public ResponseDto<ProductResponse> updateProduct(@PathVariable final Long productId, @RequestBody @Valid final ProductRequest request) {
        return ResponseDto.success(adminProductService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    public ResponseDto<Void> deleteProduct(@PathVariable final Long productId) {
        adminProductService.deleteProduct(productId);
        return ResponseDto.success();
    }
}
