package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.service.FollowService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/stats/{userId}")
    public Result<Map<String, Integer>> getStats(@PathVariable Long userId) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("followerCount", followService.countFollowers(userId));
        stats.put("followingCount", followService.countFollowings(userId));
        return Result.success(stats);
    }

    @GetMapping("/check/{followingId}")
    public Result<Boolean> checkFollowing(@PathVariable Long followingId) {
        Long currentUserId = getCurrentUserId();
        boolean isFollowing = followService.isFollowing(currentUserId, followingId);
        return Result.success(isFollowing);
    }

    @PostMapping("/{followingId}")
    public Result<Void> follow(@PathVariable Long followingId) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId.equals(followingId)) {
            throw new RuntimeException("不能关注自己");
        }
        followService.follow(currentUserId, followingId);
        return Result.success(null);
    }

    @DeleteMapping("/{followingId}")
    public Result<Void> unfollow(@PathVariable Long followingId) {
        Long currentUserId = getCurrentUserId();
        followService.unfollow(currentUserId, followingId);
        return Result.success(null);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        }
        throw new com.example.blog.exception.UnauthorizedException("未登录");
    }
}