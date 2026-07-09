
package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String summary;

    @NotBlank(message = "内容不能为空")
    private String content;

    private Long categoryId;

    private Boolean isPublished;
}
