
package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String summary;
    private String content;
    private Long authorId;
    private Long categoryId;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private String authorNickname;
    @TableField(exist = false)
    private String authorAvatar;
    @TableField(exist = false)
    private String authorBio;
}
