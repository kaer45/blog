package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.service.CommentLikeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment-likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @GetMapping("/count/{commentId}")
    public Result<Integer> countLikes(@PathVariable Long commentId) {
        return Result.success(commentLikeService.countLikes(commentId));
    }

    @GetMapping("/check/{commentId}")
    public Result<Boolean> checkLiked(@PathVariable Long commentId) {
        Long currentUserId = getCurrentUserId();
        boolean isLiked = commentLikeService.isLiked(currentUserId, commentId);
        return Result.success(isLiked);
    }

    @PostMapping("/{commentId}")
    public Result<Void> like(@PathVariable Long commentId) {
        Long currentUserId = getCurrentUserId();
        commentLikeService.like(currentUserId, commentId);
        return Result.success(null);
    }

    @DeleteMapping("/{commentId}")
    public Result<Void> unlike(@PathVariable Long commentId) {
        Long currentUserId = getCurrentUserId();
        commentLikeService.unlike(currentUserId, commentId);
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