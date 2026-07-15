
package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.mapper.CommentMapper;
import com.example.blog.service.CommentService;
import com.example.blog.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final UserService userService;

    public CommentServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private void fillAuthorInfo(Comment comment) {
        if (comment != null && comment.getAuthorId() != null) {
            User user = userService.getById(comment.getAuthorId());
            if (user != null) {
                comment.setAuthorName(user.getUsername());
                comment.setAuthorNickname(user.getNickname());
                comment.setAuthorAvatar(user.getAvatar());
            }
        }
        if (comment != null && comment.getReplyToUserId() != null) {
            User replyToUser = userService.getById(comment.getReplyToUserId());
            if (replyToUser != null) {
                comment.setReplyToName(replyToUser.getUsername());
                comment.setReplyToNickname(replyToUser.getNickname());
            }
        } else if (comment != null && comment.getParentId() != null) {
            Comment parent = getById(comment.getParentId());
            if (parent != null && parent.getAuthorId() != null) {
                User parentUser = userService.getById(parent.getAuthorId());
                if (parentUser != null) {
                    comment.setReplyToName(parentUser.getUsername());
                    comment.setReplyToNickname(parentUser.getNickname());
                }
            }
        }
    }

    private void fillAuthorInfo(List<Comment> comments) {
        if (comments != null) {
            for (Comment comment : comments) {
                fillAuthorInfo(comment);
            }
        }
    }

    @Override
    public List<Comment> findByArticle(Long articleId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId)
                .orderByAsc(Comment::getCreatedAt);
        List<Comment> allComments = list(wrapper);
        fillAuthorInfo(allComments);
        
        // Build 2-level tree: top-level comments + their replies
        Map<Long, Comment> commentMap = new HashMap<>();
        List<Comment> topLevelComments = new ArrayList<>();
        
        for (Comment comment : allComments) {
            comment.setChildren(new ArrayList<>());
            commentMap.put(comment.getId(), comment);
        }
        
        for (Comment comment : allComments) {
            if (comment.getParentId() == null) {
                topLevelComments.add(comment);
            } else {
                Comment parent = commentMap.get(comment.getParentId());
                if (parent != null) {
                    parent.getChildren().add(comment);
                } else {
                    // Parent not found in this batch, treat as top-level
                    topLevelComments.add(comment);
                }
            }
        }
        
        return topLevelComments;
    }

    @Override
    public Comment create(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        save(comment);
        fillAuthorInfo(comment);
        return comment;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }
}
