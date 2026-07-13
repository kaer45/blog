package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleLike;
import com.example.blog.mapper.ArticleLikeMapper;
import com.example.blog.service.ArticleLikeService;
import com.example.blog.service.ArticleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    private final ArticleService articleService;

    public ArticleLikeServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public int countLikes(Long articleId) {
        return baseMapper.countLikes(articleId);
    }

    @Override
    public boolean isLiked(Long userId, Long articleId) {
        return baseMapper.isLiked(userId, articleId) > 0;
    }

    @Override
    public void like(Long userId, Long articleId) {
        if (isLiked(userId, articleId)) {
            return;
        }
        ArticleLike like = new ArticleLike();
        like.setUserId(userId);
        like.setArticleId(articleId);
        like.setCreatedAt(LocalDateTime.now());
        save(like);
        
        Article article = articleService.getById(articleId);
        if (article != null) {
            article.setLikeCount(article.getLikeCount() != null ? article.getLikeCount() + 1 : 1);
            articleService.updateById(article);
        }
    }

    @Override
    public void unlike(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleLike::getUserId, userId)
               .eq(ArticleLike::getArticleId, articleId);
        remove(wrapper);
        
        Article article = articleService.getById(articleId);
        if (article != null) {
            article.setLikeCount(article.getLikeCount() != null ? Math.max(0, article.getLikeCount() - 1) : 0);
            articleService.updateById(article);
        }
    }

    @Override
    public List<Article> findLikedArticles(Long userId) {
        return baseMapper.findLikedArticles(userId);
    }
}