
package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long articleId;

    private Long parentId;

    @NotBlank(message = "评论内容不能为空")
    private String content;
}
