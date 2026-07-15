
package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.PageResult;
import com.example.blog.entity.Article;
import com.example.blog.entity.User;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.service.ArticleService;
import com.example.blog.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final UserService userService;

    public ArticleServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private void fillAuthorInfo(Article article) {
        if (article != null && article.getAuthorId() != null) {
            User user = userService.getById(article.getAuthorId());
            if (user != null) {
                article.setAuthorName(user.getUsername());
                article.setAuthorNickname(user.getNickname());
                article.setAuthorAvatar(user.getAvatar());
                article.setAuthorBio(user.getBio());
            }
        }
    }

    private void fillAuthorInfo(List<Article> articles) {
        if (articles != null) {
            for (Article article : articles) {
                fillAuthorInfo(article);
            }
        }
    }

    @Override
    public List<Article> findPublishedArticles() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        List<Article> articles = list(wrapper);
        fillAuthorInfo(articles);
        return articles;
    }

    @Override
    public List<Article> findByCategory(Long categoryId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, categoryId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        List<Article> articles = list(wrapper);
        fillAuthorInfo(articles);
        return articles;
    }

    @Override
    public List<Article> findByAuthor(Long authorId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, authorId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getCreatedAt);
        List<Article> articles = list(wrapper);
        fillAuthorInfo(articles);
        return articles;
    }

    @Override
    public List<Article> findDraftsByAuthor(Long authorId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, authorId)
                .eq(Article::getIsPublished, false)
                .orderByDesc(Article::getCreatedAt);
        List<Article> articles = list(wrapper);
        fillAuthorInfo(articles);
        return articles;
    }

    @Override
    public Article findByIdWithAuthor(Long id) {
        Article article = getById(id);
        fillAuthorInfo(article);
        return article;
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

    @Override
    public PageResult<Article> findPublishedArticles(int page, int size) {
        Page<Article> pageQuery = new Page<>(page, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        IPage<Article> result = page(pageQuery, wrapper);
        fillAuthorInfo(result.getRecords());
        return new PageResult<>(result.getRecords(), page, size, result.getTotal());
    }

    @Override
    public PageResult<Article> findByCategory(Long categoryId, int page, int size) {
        Page<Article> pageQuery = new Page<>(page, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, categoryId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getPublishedAt);
        IPage<Article> result = page(pageQuery, wrapper);
        fillAuthorInfo(result.getRecords());
        return new PageResult<>(result.getRecords(), page, size, result.getTotal());
    }

    @Override
    public PageResult<Article> findByAuthor(Long authorId, int page, int size) {
        Page<Article> pageQuery = new Page<>(page, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, authorId)
                .eq(Article::getIsPublished, true)
                .orderByDesc(Article::getCreatedAt);
        IPage<Article> result = page(pageQuery, wrapper);
        fillAuthorInfo(result.getRecords());
        return new PageResult<>(result.getRecords(), page, size, result.getTotal());
    }
}
