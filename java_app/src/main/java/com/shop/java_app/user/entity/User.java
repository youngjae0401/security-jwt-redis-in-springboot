package com.shop.java_app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.java_app.auth.entity.Role;
import com.shop.java_app.common.entity.BaseTimeEntity;
import com.shop.java_app.user.dto.UserRequest;
import com.shop.java_app.user.dto.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP, email = 'withdrawn', phone_number = 'withdrawn' WHERE id = ?")
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String name;

    @NonNull
    @JsonIgnore
    private String password;

    @NonNull
    @Column(unique = true)
    @JsonIgnore
    private String phoneNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime deletedAt;

    public UserResponse toResponse() {
        return UserResponse.builder()
                .userId(id)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updatePhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateUser(final UserRequest request) {
        updateName(request.getName());
        updatePhoneNumber(request.getPhoneNumber());
    }
}
