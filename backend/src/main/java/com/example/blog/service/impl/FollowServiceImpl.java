package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Follow;
import com.example.blog.mapper.FollowMapper;
import com.example.blog.service.FollowService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Override
    public int countFollowers(Long userId) {
        return baseMapper.countFollowers(userId);
    }

    @Override
    public int countFollowings(Long userId) {
        return baseMapper.countFollowings(userId);
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return baseMapper.isFollowing(followerId, followingId) > 0;
    }

    @Override
    public void follow(Long followerId, Long followingId) {
        if (isFollowing(followerId, followingId)) {
            return;
        }
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        follow.setCreatedAt(LocalDateTime.now());
        save(follow);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getFollowerId, followerId)
               .eq(Follow::getFollowingId, followingId);
        remove(wrapper);
    }
}