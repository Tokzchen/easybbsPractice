package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.LikeRecord;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.LikeRecordMapper;
import com.example.easybbsweb.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeRecordMapper likeRecordMapper;

    @Override
    public boolean addLikeRecord(LikeRecord likeRecord) {
        if (
                //必传参数:Optype,ObjectId,userId
                likeRecord.getUserId() == null ||
                        likeRecord.getObjectId() == null ||
                        likeRecord.getOpType() == null ||
                        "".equals(likeRecord.getAuthorUserId()) ||
                        "".equals(likeRecord.getObjectId())) {
            return false;
        }

        Integer integer = likeRecordMapper.insertLikeRecord(likeRecord);
        return integer > 0;
    }

    @Override
    public List<LikeRecord> getLikeRecordsByUserId(String userId) {
        if (userId == null || userId.equals("")) {
            throw new BusinessException("查询点赞记录时用户id不得为空");
        }
        List<LikeRecord> likeRecords = likeRecordMapper.selectLikeRecordsByUserId(userId);
        return likeRecords;
    }

    @Override
    public Integer getArticleLikeCnt(Article article) {
        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setObjectId(article.getArticleId());
        likeRecord.setOpType(0);
        Integer likeCnt= likeRecordMapper.getArticleOrCommentLikeCount(likeRecord);
       return likeCnt;
    }

}
