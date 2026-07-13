package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
    @Select("SELECT COUNT(*) FROM follow WHERE following_id = #{userId}")
    int countFollowers(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM follow WHERE follower_id = #{userId}")
    int countFollowings(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM follow WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    int isFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);
}