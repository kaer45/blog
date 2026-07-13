package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleLikeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article-likes")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    public ArticleLikeController(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @GetMapping("/count/{articleId}")
    public Result<Integer> countLikes(@PathVariable Long articleId) {
        return Result.success(articleLikeService.countLikes(articleId));
    }

    @GetMapping("/check/{articleId}")
    public Result<Boolean> checkLiked(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        boolean isLiked = articleLikeService.isLiked(currentUserId, articleId);
        return Result.success(isLiked);
    }

    @PostMapping("/{articleId}")
    public Result<Void> like(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        articleLikeService.like(currentUserId, articleId);
        return Result.success(null);
    }

    @DeleteMapping("/{articleId}")
    public Result<Void> unlike(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        articleLikeService.unlike(currentUserId, articleId);
        return Result.success(null);
    }

    @GetMapping("/user")
    public Result<List<Article>> getLikedArticles() {
        Long currentUserId = getCurrentUserId();
        List<Article> articles = articleLikeService.findLikedArticles(currentUserId);
        return Result.success(articles);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        }
        throw new com.example.blog.exception.UnauthorizedException("未登录");
    }
}