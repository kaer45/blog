package com.example.blog.controller;

import com.example.blog.dto.Result;
import com.example.blog.entity.Category;
import com.example.blog.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categories = categoryService.findAll();
        return Result.success(categories);
    }

    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @PostMapping
    public Result<Category> create(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success(category);
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @RequestBody Category category) {
        Category existing = categoryService.getById(id);
        if (existing != null) {
            existing.setName(category.getName());
            existing.setDescription(category.getDescription());
            categoryService.updateById(existing);
        }
        return Result.success(existing);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success(null);
    }
}