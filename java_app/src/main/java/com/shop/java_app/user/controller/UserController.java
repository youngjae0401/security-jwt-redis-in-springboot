package com.shop.java_app.user.controller;

import com.shop.java_app.common.dto.ResponseDto;
import com.shop.java_app.user.dto.UserRequest;
import com.shop.java_app.user.dto.UserResponse;
import com.shop.java_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseDto<List<UserResponse>> getUsers() {
        return ResponseDto.success(userService.getUsers());
    }

    @PutMapping("/{userId}")
    public ResponseDto<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest request) {
        return ResponseDto.success(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseDto<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseDto.success();
    }
}
