
package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<Article> findPublishedArticles();
    List<Article> findByCategory(Long categoryId);
    List<Article> findByAuthor(Long authorId);
    Article findByIdWithAuthor(Long id);
    Article publish(Long id);
    Article draft(Long id);
}
