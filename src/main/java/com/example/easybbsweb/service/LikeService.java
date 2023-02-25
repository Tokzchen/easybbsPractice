package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.LikeRecord;

import java.util.List;

public interface LikeService {
    public boolean addLikeRecord(LikeRecord likeRecord);

    public List<LikeRecord> getLikeRecordsByUserId(String userId);

    Integer getArticleLikeCnt(Article article);
}
