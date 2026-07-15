package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.CommentLike;

public interface CommentLikeService extends IService<CommentLike> {
    void like(Long userId, Long commentId);
    void unlike(Long userId, Long commentId);
    boolean isLiked(Long userId, Long commentId);
    int countLikes(Long commentId);
}