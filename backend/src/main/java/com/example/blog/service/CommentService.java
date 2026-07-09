
package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> findByArticle(Long articleId);
    Comment create(Comment comment);
    void delete(Long id);
}
