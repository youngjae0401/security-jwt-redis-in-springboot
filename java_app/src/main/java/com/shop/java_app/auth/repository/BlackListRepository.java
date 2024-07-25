package com.shop.java_app.auth.repository;

import com.shop.java_app.auth.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, Long> {
    boolean existsByAccessToken(final String accessToken);
}
