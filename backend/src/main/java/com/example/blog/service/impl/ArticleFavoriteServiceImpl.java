package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleFavorite;
import com.example.blog.entity.User;
import com.example.blog.mapper.ArticleFavoriteMapper;
import com.example.blog.service.ArticleFavoriteService;
import com.example.blog.service.ArticleService;
import com.example.blog.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleFavoriteServiceImpl extends ServiceImpl<ArticleFavoriteMapper, ArticleFavorite> implements ArticleFavoriteService {

    private final ArticleService articleService;
    private final UserService userService;

    public ArticleFavoriteServiceImpl(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    public void favorite(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorite::getUserId, userId)
                .eq(ArticleFavorite::getArticleId, articleId);
        
        if (!exists(wrapper)) {
            ArticleFavorite favorite = new ArticleFavorite();
            favorite.setUserId(userId);
            favorite.setArticleId(articleId);
            favorite.setCreatedAt(LocalDateTime.now());
            save(favorite);
        }
    }

    @Override
    public void unfavorite(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorite::getUserId, userId)
                .eq(ArticleFavorite::getArticleId, articleId);
        remove(wrapper);
    }

    @Override
    public boolean isFavorited(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorite::getUserId, userId)
                .eq(ArticleFavorite::getArticleId, articleId);
        return exists(wrapper);
    }

    @Override
    public int countFavorites(Long articleId) {
        LambdaQueryWrapper<ArticleFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorite::getArticleId, articleId);
        return (int) count(wrapper);
    }

    @Override
    public List<Article> findFavoritedArticles(Long userId) {
        LambdaQueryWrapper<ArticleFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorite::getUserId, userId)
                .orderByDesc(ArticleFavorite::getCreatedAt);
        List<ArticleFavorite> favorites = list(wrapper);
        
        return favorites.stream()
                .map(f -> {
                    Article article = articleService.getById(f.getArticleId());
                    if (article != null) {
                        User author = userService.getById(article.getAuthorId());
                        if (author != null) {
                            article.setAuthorName(author.getUsername());
                            article.setAuthorNickname(author.getNickname());
                            article.setAuthorAvatar(author.getAvatar());
                            article.setAuthorBio(author.getBio());
                        }
                    }
                    return article;
                })
                .filter(a -> a != null)
                .toList();
    }
}