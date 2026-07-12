package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.entity.Comment;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CommentService;
import jakarta.validation.Valid;
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
        Comment created = commentService.create(comment);
        return Result.success(created);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success(null);
    }
}