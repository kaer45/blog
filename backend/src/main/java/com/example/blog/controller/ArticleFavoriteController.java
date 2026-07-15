package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleFavoriteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article-favorites")
public class ArticleFavoriteController {

    private final ArticleFavoriteService articleFavoriteService;

    public ArticleFavoriteController(ArticleFavoriteService articleFavoriteService) {
        this.articleFavoriteService = articleFavoriteService;
    }

    @GetMapping("/count/{articleId}")
    public Result<Integer> countFavorites(@PathVariable Long articleId) {
        return Result.success(articleFavoriteService.countFavorites(articleId));
    }

    @GetMapping("/check/{articleId}")
    public Result<Boolean> checkFavorited(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        boolean isFavorited = articleFavoriteService.isFavorited(currentUserId, articleId);
        return Result.success(isFavorited);
    }

    @PostMapping("/{articleId}")
    public Result<Void> favorite(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        articleFavoriteService.favorite(currentUserId, articleId);
        return Result.success(null);
    }

    @DeleteMapping("/{articleId}")
    public Result<Void> unfavorite(@PathVariable Long articleId) {
        Long currentUserId = getCurrentUserId();
        articleFavoriteService.unfavorite(currentUserId, articleId);
        return Result.success(null);
    }

    @GetMapping("/user")
    public Result<List<Article>> getFavoritedArticles() {
        Long currentUserId = getCurrentUserId();
        List<Article> articles = articleFavoriteService.findFavoritedArticles(currentUserId);
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