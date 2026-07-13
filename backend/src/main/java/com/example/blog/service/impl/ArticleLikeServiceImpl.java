package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleLike;
import com.example.blog.mapper.ArticleLikeMapper;
import com.example.blog.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

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
    }

    @Override
    public void unlike(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleLike::getUserId, userId)
               .eq(ArticleLike::getArticleId, articleId);
        remove(wrapper);
    }

    @Override
    public List<Article> findLikedArticles(Long userId) {
        return baseMapper.findLikedArticles(userId);
    }
}