package com.example.easybbsweb.service;

import java.util.List;

public interface FollowService {
    boolean checkFollow(Long uid,Long followee);
    void follow(Long uid, Long followee);
    void unfollow(Long uid,Long followee);
    List<Long> getFollower(Long uid);
    List<Long> getFollowee(Long uid);

}
