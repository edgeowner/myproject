package com.huboot.business.common.utils.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * websocket-
 */
@Component
@ServerEndpoint(value = "/websocket/box/record/{bid}")
public class WebSocketBoxRecordServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketBoxRecordServer.class);

    private Session session;
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();
    private static Map<String, String> sessionIds = new HashMap<String, String>();

    /**
     * 用户连接时触发
     *
     * @param session
     * @param bid
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "bid") String bid) {
        logger.info("new user connecting, bid is : " + bid);
        this.session = session;
        sessionPool.put(bid, session);
        sessionIds.put(session.getId(), bid);
    }

    /**
     * 收到信息时触发
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("receive message, user sessionid is : " + session.getId() + ", content is : " + message);
    }

    /**
     * 连接关闭触发
     */
    @OnClose
    public void onClose() {
        logger.info("close connect, user sessionid is : " + session.getId());
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     * 发生错误时触发
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("throw exception, user sessionid is : " + session.getId());
        error.printStackTrace();
    }

    /**
     * 信息发送的方法
     *
     * @param message
     * @param bid
     */
    public static void sendMessage(String message, String bid) {
        Session s = sessionPool.get(bid);
        if (s != null) {
            try {
                logger.info("send message to user, bid is : " + bid);
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("throw exception when send message to user, bid is : " + bid);
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前连接数
     *
     * @return
     */
    public static int getOnlineNum() {
        return sessionPool.size();
    }

    /**
     * 获取在线用户名以逗号隔开
     *
     * @return
     */
    public static String getOnlineUsers() {
        StringBuffer users = new StringBuffer();
        for (String key : sessionIds.keySet()) {
            users.append(sessionIds.get(key) + ",");
        }
        return users.toString();
    }

    /**
     * 信息群发
     *
     * @param msg
     */
    public static void sendAll(String msg) {
        for (String key : sessionIds.keySet()) {
            sendMessage(msg, sessionIds.get(key));
        }
    }

    /**
     * 多个人发送给指定的几个用户
     *
     * @param msg
     * @param persons 用户
     */

    public static void sendMany(String msg, List<String> persons) {
        for (String bid : persons) {
            sendMessage(msg, bid);
        }

    }
}