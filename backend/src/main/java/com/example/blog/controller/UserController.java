package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.dto.UserDTO;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/public")
    public Result<User> getPublicProfile() {
        User user = userService.list().stream().findFirst().orElse(null);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping("/profile")
    public Result<User> getProfile() {
        Long currentUserId = getCurrentUserId();
        User user = userService.getById(currentUserId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result<User> updateProfile(@Valid @RequestBody UserDTO dto) {
        Long currentUserId = getCurrentUserId();
        User user = userService.getById(currentUserId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        if (dto.getGithubUrl() != null) {
            user.setGithubUrl(dto.getGithubUrl());
        }
        user.setUpdatedAt(java.time.LocalDateTime.now());
        
        userService.updateById(user);
        user.setPassword(null);
        return Result.success(user);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        }
        throw new com.example.blog.exception.UnauthorizedException("未登录");
    }
}