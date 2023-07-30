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
public class ForumCommentService {
    @Resource
    ForumArticleRepository forumArticleRepository;
    @Resource
    ForumSecondCommentService secondCommentService;
    @Resource
    ForumUserService userService;

    public void deleteComment(ForumArticleReq dto, String id) {
        ForumArticle forumArticle = forumArticleRepository.findById(dto.getId());
        ArticleComment comment = getCommentById(forumArticle, id);
        ALlNotLikeComment(comment);
        for (SecondComment secondComment : comment.getSecondComments()) {
            secondCommentService.ALlNotLikeSecondComment(secondComment);
        }
        updateComment(dto);
    }

    public void likeComment(ForumArticleReq dto, String email, String CommentId) {
        userService.likeComment (email, CommentId);
        updateComment(dto);
    }

    public void unlikeComment(ForumArticleReq dto, String email, String CommentId) {
        userService.unlikeComment(email, CommentId);
        updateComment(dto);
    }

    /**
     * 更新评论
     */
    public void updateComment(ForumArticleReq dto) {
        ForumArticle forumArticle = new ForumArticle();
        BeanUtils.copyProperties(dto, forumArticle);
        forumArticleRepository.updateComment(forumArticle);
    }


    public void ALlNotLikeComment(ArticleComment comment) {
        comment.getUserWhoLikes().forEach(user -> userService.likeComment(user, comment.getId()));
    }

    /**
     * 在文章中通过id获取二级评论
     */
    public ArticleComment getCommentById(ForumArticle dto, String CommentId) {
        for (ArticleComment comment : dto.getComments()) {
            if (comment.getId().equals(CommentId)) {
                return comment;
            }
        }
        return null;
    }
}
