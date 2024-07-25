package com.shop.java_app.user.controller;

import com.shop.java_app.common.dto.ResponseDto;
import com.shop.java_app.user.dto.UserRequest;
import com.shop.java_app.user.dto.UserResponse;
import com.shop.java_app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseDto<List<UserResponse>> getUsers() {
        return ResponseDto.success(userService.getUsers());
    }

    @PutMapping("/{userId}")
    public ResponseDto<UserResponse> updateUser(@PathVariable final Long userId, @RequestBody @Valid final UserRequest request) {
        return ResponseDto.success(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseDto<Void> deleteUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
        return ResponseDto.success();
    }
}
