package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentLike;
import com.example.blog.mapper.CommentLikeMapper;
import com.example.blog.service.CommentLikeService;
import com.example.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {

    private final CommentService commentService;

    public CommentLikeServiceImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void like(Long userId, Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getUserId, userId)
                .eq(CommentLike::getCommentId, commentId);
        
        if (!exists(wrapper)) {
            CommentLike like = new CommentLike();
            like.setUserId(userId);
            like.setCommentId(commentId);
            like.setCreatedAt(LocalDateTime.now());
            save(like);
            
            Comment comment = commentService.getById(commentId);
            if (comment != null) {
                comment.setLikeCount(comment.getLikeCount() != null ? comment.getLikeCount() + 1 : 1);
                commentService.updateById(comment);
            }
        }
    }

    @Override
    public void unlike(Long userId, Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getUserId, userId)
                .eq(CommentLike::getCommentId, commentId);
        
        if (exists(wrapper)) {
            remove(wrapper);
            
            Comment comment = commentService.getById(commentId);
            if (comment != null) {
                comment.setLikeCount(comment.getLikeCount() != null && comment.getLikeCount() > 0 ? comment.getLikeCount() - 1 : 0);
                commentService.updateById(comment);
            }
        }
    }

    @Override
    public boolean isLiked(Long userId, Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getUserId, userId)
                .eq(CommentLike::getCommentId, commentId);
        return exists(wrapper);
    }

    @Override
    public int countLikes(Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getCommentId, commentId);
        return (int) count(wrapper);
    }
}