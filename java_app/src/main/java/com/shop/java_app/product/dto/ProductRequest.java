package com.shop.java_app.product.dto;

import com.shop.java_app.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductRequest {
    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    @NotNull(message = "상품 금액을 입력해주세요.")
    private int price;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .build();
    }
}
