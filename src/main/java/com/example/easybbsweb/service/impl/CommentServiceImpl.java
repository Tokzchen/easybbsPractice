package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.Comment;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.ForumCommentMapper;
import com.example.easybbsweb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ForumCommentMapper forumCommentMapper;
    @Override
    public List<Comment> getArticleCommentByArticleId(Article article) {
        String articleId=article.getArticleId();
        if(articleId==null||articleId.equals("")){
            throw new BusinessException("文章不存在!");
        }
        //首先查出这篇文章的所有一级评论
        List<Comment> comments = forumCommentMapper.selectCommentsByArticleId(articleId);
        //然后对所有一级评论进行子评论的查找
        for(Comment comment:comments){
            List<Comment> secondComments = forumCommentMapper.selectSecondCommentsBypBoardId(comment);
            comment.setChild(secondComments);
        }

        //分页处理
        Integer pageNo = article.getPageNo();
        List<Comment> returnComments=new ArrayList<>();
        //这里约定一页评论最多25条
        Integer startIndex=(pageNo-1)*25;
        for(int i=startIndex;i<startIndex+25;i++){
            if(i>comments.size()-1){
                break;
            }
            returnComments.add(comments.get(i));
        }

        return returnComments;

    }
}
