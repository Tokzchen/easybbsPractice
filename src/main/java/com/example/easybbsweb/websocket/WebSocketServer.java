package com.example.easybbsweb.websocket;

import cn.hutool.json.JSONUtil;
import com.example.easybbsweb.domain.entity.YoufaMail;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.utils.TokenUtil;
import com.example.easybbsweb.websocket.dto.MailNotice;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/mail/{token}")
public class WebSocketServer {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private Long uid;
    private static final CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static final Map<Long, Session> sessionPool = new HashMap<Long, Session>();
    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "token") String token) {
        boolean verify = TokenUtil.verify(token);
        if(!verify){
            throw new SystemException("未登录");
        }
        String userOrUniId = TokenUtil.getCurrentUserOrUniId(token);
        long uid = Long.parseLong(userOrUniId);
        this.session=session;
        this.uid=uid;
        try {
            this.session=session;
            this.uid=uid;
            webSockets.add(this);
            sessionPool.put(uid, session);
            log.info("websocket消息: 有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }
    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        log.info("websocket消息: 收到客户端消息:" + message);
        String id = session.getId();
        log.info("websocket的id是{}",id);
        session.getAsyncRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) {
        try {
            log.info("链接关闭:{}",session.isOpen());
            webSockets.remove(this);
            sessionPool.remove(this.uid);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 此为单点消息
     */
    public void sendOneMessage(Long userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("websocket消: 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOneMail(Long receiver){
        Session session = sessionPool.get(receiver);
        if (session != null && session.isOpen()){
            try {
                log.info("提醒客户端有单点消息,receiver：{}",receiver);
                session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MailNotice.isSimple()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMultiPleMail(List<Long> uids){
        for(Long uid:uids){
            Session session = sessionPool.get(uid);
            if (session != null && session.isOpen()){
                try {
                    log.info("提醒客户端有单点消息,receiver：{}",uid);
                    session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MailNotice.isSimple()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void publishAnnouncement(){
        Set<Long> longs = sessionPool.keySet();
        longs.forEach(l->{
            Session session = sessionPool.get(l);
            if (session != null && session.isOpen()){
                try {
                    log.info("提醒客户端有公告,receiver：{}",l);
                    session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MailNotice.isAnnouncement()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

