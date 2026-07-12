
package com.example.blog.controller;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.Result;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Result<List<Article>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId) {
        List<Article> articles;
        if (authorId != null) {
            articles = articleService.findByAuthor(authorId);
        } else if (categoryId != null) {
            articles = articleService.findByCategory(categoryId);
        } else {
            articles = articleService.findPublishedArticles();
        }
        return Result.success(articles);
    }

    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Long id) {
        Article article = articleService.findByIdWithAuthor(id);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            articleService.updateById(article);
        }
        return Result.success(article);
    }

    @PostMapping
    public Result<Article> create(@Valid @RequestBody ArticleDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setContent(dto.getContent());
        article.setCategoryId(dto.getCategoryId());
        article.setAuthorId(getCurrentUserId());
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
        article.setIsPublished(dto.getIsPublished() != null && dto.getIsPublished());
        article.setCreatedAt(java.time.LocalDateTime.now());
        article.setUpdatedAt(java.time.LocalDateTime.now());
        if (article.getIsPublished()) {
            article.setPublishedAt(java.time.LocalDateTime.now());
        }
        articleService.save(article);
        return Result.success(article);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        }
        throw new com.example.blog.exception.UnauthorizedException("未登录");
    }

    @PutMapping("/{id}")
    public Result<Article> update(@PathVariable Long id, @Valid @RequestBody ArticleDTO dto) {
        Article article = articleService.getById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        Long currentUserId = getCurrentUserId();
        if (!article.getAuthorId().equals(currentUserId)) {
            throw new com.example.blog.exception.ForbiddenException("无权修改该文章");
        }
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setContent(dto.getContent());
        article.setCategoryId(dto.getCategoryId());
        article.setUpdatedAt(java.time.LocalDateTime.now());
        articleService.updateById(article);
        return Result.success(article);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        Long currentUserId = getCurrentUserId();
        if (!article.getAuthorId().equals(currentUserId)) {
            throw new com.example.blog.exception.ForbiddenException("无权删除该文章");
        }
        articleService.removeById(id);
        return Result.success(null);
    }

    @PostMapping("/{id}/publish")
    public Result<Article> publish(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        Long currentUserId = getCurrentUserId();
        if (!article.getAuthorId().equals(currentUserId)) {
            throw new com.example.blog.exception.ForbiddenException("无权发布该文章");
        }
        article = articleService.publish(id);
        return Result.success(article);
    }

    @PostMapping("/{id}/draft")
    public Result<Article> draft(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        Long currentUserId = getCurrentUserId();
        if (!article.getAuthorId().equals(currentUserId)) {
            throw new com.example.blog.exception.ForbiddenException("无权修改该文章状态");
        }
        article = articleService.draft(id);
        return Result.success(article);
    }
}
