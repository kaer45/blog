package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("contact")
public class Contact {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Boolean replied;
    private String replyContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}