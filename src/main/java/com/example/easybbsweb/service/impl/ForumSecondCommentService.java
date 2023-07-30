package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.controller.request.forum.ForumArticleReq;
import com.example.easybbsweb.repository.ForumArticleRepository;
import com.example.easybbsweb.repository.entity.ArticleComment;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.example.easybbsweb.repository.entity.SecondComment;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ForumSecondCommentService {
    @Resource
    ForumArticleRepository forumArticleRepository;
    @Resource
    ForumUserService userService;

    public void deleteSecondComment(ForumArticleReq dto, String id) {
        ForumArticle forumArticle = forumArticleRepository.findById(dto.getId());
        for (ArticleComment comment : forumArticle.getComments()) {
            for (SecondComment secondComment : comment.getSecondComments()) {
                ALlNotLikeSecondComment(secondComment);
            }


        }
        updateSecondComment(dto);
    }

    public void likeSecondComment(ForumArticleReq dto, String email, String secondCommentId) {
        userService.likeSecondComment(email,secondCommentId);
        updateSecondComment(dto);
    }
    public void unlikeSecondComment(ForumArticleReq dto, String email, String secondCommentId) {
        userService.unlikeComment(email,secondCommentId);
        updateSecondComment(dto);
    }

    /**
     * 更新评论
     */
    public void updateSecondComment(ForumArticleReq dto) {
        ForumArticle forumArticle = new ForumArticle();
        BeanUtils.copyProperties(dto, forumArticle);
        forumArticleRepository.updateComment(forumArticle);
    }


    public void ALlNotLikeSecondComment(SecondComment secondComment) {
        secondComment.getUserWhoLikes().forEach(user -> userService.likeSecondComment(user, secondComment.getId()));
    }

    /**
     *
     *  在文章中通过id获取二级评论
     */
    public SecondComment getSecondCommentById(ForumArticleReq dto, String secondCommentId) {
        for (ArticleComment comment : dto.getComments()) {
            for (SecondComment secondComment : comment.getSecondComments()) {
                if (secondComment.getId().equals(secondCommentId)) {
                    return secondComment;
                }
            }
        }
        return null;
    }
}
