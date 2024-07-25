package com.shop.java_app.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private String name;
    private int price;
}
