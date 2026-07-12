package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.entity.Comment;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CommentService;
import com.example.blog.entity.Article;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;

    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping("/article/{articleId}")
    public Result<List<Comment>> listByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.findByArticle(articleId);
        return Result.success(comments);
    }

    @PostMapping
    public Result<Comment> create(@Valid @RequestBody Comment comment) {
        Long currentUserId = getCurrentUserId();
        
        Article article = articleService.getById(comment.getArticleId());
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        
        comment.setAuthorId(currentUserId);
        Comment created = commentService.create(comment);
        return Result.success(created);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        Long currentUserId = getCurrentUserId();
        if (!comment.getAuthorId().equals(currentUserId)) {
            throw new com.example.blog.exception.ForbiddenException("无权删除该评论");
        }
        commentService.delete(id);
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