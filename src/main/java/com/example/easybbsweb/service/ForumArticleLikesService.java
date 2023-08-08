package com.example.easybbsweb.service;

public interface ForumArticleLikesService {
    boolean checkLike(Long uid,Long articleId);

    void sendLike(Long uid, Long id, Boolean like, String identifies);

    void likeOrUnlike(Long uid, Long id, Boolean like, String identifies);

    void identifies(String identifies);

}
