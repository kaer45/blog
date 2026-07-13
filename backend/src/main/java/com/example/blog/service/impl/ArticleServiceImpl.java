
package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Article;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.service.ArticleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<Article> findPublishedArticles() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        return list(wrapper);
    }

    @Override
    public List<Article> findByCategory(Long categoryId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, categoryId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        return list(wrapper);
    }

    @Override
    public List<Article> findByAuthor(Long authorId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, authorId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getCreatedAt);
        return list(wrapper);
    }

    @Override
    public List<Article> findDraftsByAuthor(Long authorId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, authorId)
                .eq(Article::getIsPublished, false)
                .orderByDesc(Article::getCreatedAt);
        return list(wrapper);
    }

    @Override
    public Article findByIdWithAuthor(Long id) {
        return getById(id);
    }

    @Override
    public Article publish(Long id) {
        Article article = getById(id);
        if (article != null) {
            article.setIsPublished(true);
            article.setPublishedAt(LocalDateTime.now());
            article.setUpdatedAt(LocalDateTime.now());
            updateById(article);
        }
        return article;
    }

    @Override
    public Article draft(Long id) {
        Article article = getById(id);
        if (article != null) {
            article.setIsPublished(false);
            article.setUpdatedAt(LocalDateTime.now());
            updateById(article);
        }
        return article;
    }
}
