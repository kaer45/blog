package com.example.blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> data;
    private Integer page;
    private Integer size;
    private Long total;
    private Integer totalPages;

    public PageResult(List<T> data, Integer page, Integer size, Long total) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
    }
}