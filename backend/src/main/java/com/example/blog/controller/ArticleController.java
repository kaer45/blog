
package com.example.blog.controller;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.PageResult;
import com.example.blog.dto.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.Category;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    public ArticleController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result<PageResult<Article>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean draft,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        if (draft != null && draft) {
            List<Article> articles = articleService.findDraftsByAuthor(getCurrentUserId());
            long total = articles.size();
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, articles.size());
            List<Article> pageData = fromIndex < articles.size() ? articles.subList(fromIndex, toIndex) : List.of();
            return Result.success(new PageResult<>(pageData, page, size, total));
        } else if (authorId != null) {
            return Result.success(articleService.findByAuthor(authorId, page, size));
        } else if (categoryId != null) {
            return Result.success(articleService.findByCategory(categoryId, page, size));
        } else {
            return Result.success(articleService.findPublishedArticles(page, size));
        }
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
        
        if (article.getIsPublished() && article.getCategoryId() != null) {
            updateCategoryArticleCount(article.getCategoryId(), 1);
        }
        
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
        
        Long oldCategoryId = article.getCategoryId();
        boolean wasPublished = article.getIsPublished();
        
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setContent(dto.getContent());
        article.setCategoryId(dto.getCategoryId());
        article.setUpdatedAt(java.time.LocalDateTime.now());
        
        if (dto.getIsPublished() != null) {
            article.setIsPublished(dto.getIsPublished());
            if (dto.getIsPublished()) {
                article.setPublishedAt(java.time.LocalDateTime.now());
            }
        }
        
        articleService.updateById(article);
        
        if (wasPublished && oldCategoryId != null && !oldCategoryId.equals(dto.getCategoryId())) {
            updateCategoryArticleCount(oldCategoryId, -1);
        }
        if (article.getIsPublished() && dto.getCategoryId() != null && !dto.getCategoryId().equals(oldCategoryId)) {
            updateCategoryArticleCount(dto.getCategoryId(), 1);
        }
        
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
        
        if (article.getIsPublished() && article.getCategoryId() != null) {
            updateCategoryArticleCount(article.getCategoryId(), -1);
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
        
        if (article.getCategoryId() != null) {
            updateCategoryArticleCount(article.getCategoryId(), 1);
        }
        
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
        
        if (article.getIsPublished() && article.getCategoryId() != null) {
            updateCategoryArticleCount(article.getCategoryId(), -1);
        }
        
        article = articleService.draft(id);
        return Result.success(article);
    }
    
    private void updateCategoryArticleCount(Long categoryId, int delta) {
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            category.setArticleCount(category.getArticleCount() + delta);
            if (category.getArticleCount() < 0) {
                category.setArticleCount(0);
            }
            categoryService.updateById(category);
        }
    }
}
