package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
    @Select("SELECT COUNT(*) FROM article_like WHERE article_id = #{articleId}")
    int countLikes(@Param("articleId") Long articleId);

    @Select("SELECT COUNT(*) FROM article_like WHERE user_id = #{userId} AND article_id = #{articleId}")
    int isLiked(@Param("userId") Long userId, @Param("articleId") Long articleId);

    @Select("SELECT a.* FROM article a JOIN article_like al ON a.id = al.article_id WHERE al.user_id = #{userId} ORDER BY al.created_at DESC")
    List<com.example.blog.entity.Article> findLikedArticles(@Param("userId") Long userId);
}