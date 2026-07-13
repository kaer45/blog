package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Follow;

public interface FollowService extends IService<Follow> {
    int countFollowers(Long userId);
    int countFollowings(Long userId);
    boolean isFollowing(Long followerId, Long followingId);
    void follow(Long followerId, Long followingId);
    void unfollow(Long followerId, Long followingId);
}