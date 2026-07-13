package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.ArticleLike;

import java.util.List;

public interface ArticleLikeService extends IService<ArticleLike> {
    int countLikes(Long articleId);
    boolean isLiked(Long userId, Long articleId);
    void like(Long userId, Long articleId);
    void unlike(Long userId, Long articleId);
    List<com.example.blog.entity.Article> findLikedArticles(Long userId);
}