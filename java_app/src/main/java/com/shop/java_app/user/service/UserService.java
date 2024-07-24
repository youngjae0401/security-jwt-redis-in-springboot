package com.shop.java_app.user.service;

import com.shop.java_app.common.exception.ErrorCode;
import com.shop.java_app.common.exception.NotFoundException;
import com.shop.java_app.user.dto.UserRequest;
import com.shop.java_app.user.dto.UserResponse;
import com.shop.java_app.user.entity.User;
import com.shop.java_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(User::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));
        user.updateUser(request);
        return user.toResponse();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));
        userRepository.delete(user);
    }
}
