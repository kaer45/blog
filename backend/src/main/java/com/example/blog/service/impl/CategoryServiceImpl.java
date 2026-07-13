
package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Category;
import com.example.blog.entity.Article;
import com.example.blog.mapper.CategoryMapper;
import com.example.blog.service.CategoryService;
import com.example.blog.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final ArticleService articleService;

    public CategoryServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = list();
        for (Category category : categories) {
            int count = (int) articleService.count(new LambdaQueryWrapper<Article>()
                    .eq(Article::getCategoryId, category.getId())
                    .eq(Article::getIsPublished, true));
            category.setArticleCount(count);
        }
        return categories;
    }
}
