package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.LikeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface LikeRecordMapper {
    public Integer insertLikeRecord(LikeRecord likeRecord);

    @Select("select * from like_record where user_id=#{userId}")
    public List<LikeRecord> selectLikeRecordsByUserId(String userId);

    @Select("select count(*) from like_record where object_id=#{objectId} and op_type=#{opType}")
    public Integer getArticleOrCommentLikeCount(LikeRecord likeRecord);
}
