
package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long articleId;
    private Long authorId;
    private Long parentId;
    private Long replyToUserId;
    private String content;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private String authorNickname;
    @TableField(exist = false)
    private String authorAvatar;
    @TableField(exist = false)
    private String replyToName;
    @TableField(exist = false)
    private String replyToNickname;
    @TableField(exist = false)
    private List<Comment> children;
}
