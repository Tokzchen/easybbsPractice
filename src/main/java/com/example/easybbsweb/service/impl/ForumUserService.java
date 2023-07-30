package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.controller.ForumUserController;
import com.example.easybbsweb.repository.ForumUserRepository;
import com.example.easybbsweb.repository.entity.ForumUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumUserService {
    @Resource
    ForumUserRepository forumUserRepository;

    public ForumUser getForumUserByEmail(String email) {
        return forumUserRepository.getForumUserByEmail(email);
    }

    public void saveForumUser(String email) {
        forumUserRepository.insert(new ForumUser(email, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
    }

    /**
     * 关注
     *
     * @param FollowerEmail   追随者Email
     * @param BeFollowedEmail 被追随者Email
     */
    public void follow(String FollowerEmail, String BeFollowedEmail) {
        ForumUser follower = getForumUserByEmail(FollowerEmail);
        follower.getFollowing().add(BeFollowedEmail);
        forumUserRepository.save(follower);
        ForumUser beFollowed = getForumUserByEmail(FollowerEmail);
        beFollowed.getFollowers().add(FollowerEmail);
        forumUserRepository.save(beFollowed);
    }

    /**
     * 取关
     *
     * @param FollowerEmail   追随者Email
     * @param BeFollowedEmail 被追随者Email
     */
    public void unfollow(String FollowerEmail, String BeFollowedEmail) {
        ForumUser follower = getForumUserByEmail(FollowerEmail);
        follower.getFollowing().remove(BeFollowedEmail);
        forumUserRepository.save(follower);
        ForumUser beFollowed = getForumUserByEmail(FollowerEmail);
        beFollowed.getFollowers().remove(FollowerEmail);
        forumUserRepository.save(beFollowed);
    }

    public boolean isFollow(String FollowerEmail, String BeFollowedEmail) {
        ForumUser follower = getForumUserByEmail(FollowerEmail);
        return follower.getFollowing().contains(BeFollowedEmail);
    }

    /**
     * 发布文章
     *
     * @param email     作者邮箱
     * @param ArticleId 文章id
     */
    public void addArticle(String email, String ArticleId) {
        ForumUser author = getForumUserByEmail(email);
        author.getArticles().add(ArticleId);
        forumUserRepository.save(author);
    }

    /**
     * 文章被删除 或者 不喜欢
     *
     */
    public void unlikeArticle(String email,String ArticleId){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeArticles().remove(ArticleId);
        forumUserRepository.save(author);
    }

    /**
     *  喜欢文章
     */
    public void likeArticle(String email,String ArticleId){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeArticles().add(ArticleId);
        forumUserRepository.save(author);
    }

    /**
     * 评论被删除 或者 不喜欢
     *
     */
    public void unlikeComment(String email,String id){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeComments().remove(id);
        forumUserRepository.save(author);
    }

    /**
     *  喜欢评论
     */
    public void likeComment(String email,String id){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeComments().add(id);
        forumUserRepository.save(author);
    }
    /**
     * 二级评论
     *
     */
    public void unlikeSecondComment(String email,String id){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeSecondComment().remove(id);
        forumUserRepository.save(author);
    }

    /**
     *  二级评论
     */
    public void likeSecondComment(String email,String id){
        ForumUser author = getForumUserByEmail(email);
        author.getLikeSecondComment().add(id);
        forumUserRepository.save(author);
    }
    /**
     * 删除文章
     *
     * @param email     作者邮箱
     * @param ArticleId 文章id
     */
    public void deleteArticle(String email, String ArticleId) {
        ForumUser author = getForumUserByEmail(email);
        author.getArticles().remove(ArticleId);
        forumUserRepository.save(author);
    }


    public List<String> getFollowing(String email) {
        ForumUser forumUser = getForumUserByEmail(email);
        return forumUser.getFollowing();
    }
    public List<String> getFollowers(String email) {
        ForumUser forumUser = getForumUserByEmail(email);
        return forumUser.getFollowers();
    }

    public Integer getArticlesCount(String email) {
        ForumUser forumUser = getForumUserByEmail(email);
        return forumUser.getArticles().size();
    }
}
