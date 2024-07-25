package com.shop.java_app.product.entity;

import com.shop.java_app.common.entity.BaseTimeEntity;
import com.shop.java_app.product.dto.ProductRequest;
import com.shop.java_app.product.dto.ProductResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE products SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "products")
public class Product extends BaseTimeEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int price;

    private LocalDateTime deletedAt;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .name(name)
                .price(price)
                .build();
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updatePrice(final int price) {
        this.price = price;
    }

    public void updateProduct(final ProductRequest request) {
        updateName(request.getName());
        updatePrice(request.getPrice());
    }
}
