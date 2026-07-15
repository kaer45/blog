package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleFavorite;

import java.util.List;

public interface ArticleFavoriteService extends IService<ArticleFavorite> {
    void favorite(Long userId, Long articleId);
    void unfavorite(Long userId, Long articleId);
    boolean isFavorited(Long userId, Long articleId);
    int countFavorites(Long articleId);
    List<Article> findFavoritedArticles(Long userId);
}