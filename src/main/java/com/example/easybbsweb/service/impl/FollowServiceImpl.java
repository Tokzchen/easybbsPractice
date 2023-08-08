package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easybbsweb.domain.entity.Follow;
import com.example.easybbsweb.mapper.FollowMapper;
import com.example.easybbsweb.service.FollowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FollowServiceImpl implements FollowService {
    @Resource
    FollowMapper mapper;
    @Override
    public boolean checkFollow(Long uid, Long followee) {
        return mapper.exists(new QueryWrapper<Follow>()
                .eq("follower", uid).eq("followee", followee));
}

    @Override
    public void follow(Long uid, Long followee) {

        mapper.insert(new Follow().setFollower(uid).setFollowee(followee));
    }

    @Override
    public void unfollow(Long uid, Long followee) {
        mapper.delete(new QueryWrapper<Follow>()
                .eq("follower", uid).eq("followee", followee));
    }

    @Override
    public List<Long> getFollower(Long uid) {
        return mapper.selectList(new QueryWrapper<Follow>().eq("followee", uid)).stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    @Override
    public List<Long> getFollowee(Long uid) {
        return mapper.selectList(new QueryWrapper<Follow>().eq("follower", uid)).stream().map(Follow::getFollowee).collect(Collectors.toList());

    }
}
