package com.huboot.business.common.utils.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Component
@ServerEndpoint(value = "/websocket/box/port/{bid}/{sn}")
public class WebSocketBoxPortServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketBoxPortServer.class);

    private Session session;
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();
    private static Map<String, String> sessionIds = new HashMap<String, String>();
    private static Map<String, Set<String>> snMaps = new HashMap<String, Set<String>>();

    /**
     * 用户连接时触发
     *
     * @param session
     * @param bid
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "bid") String bid, @PathParam(value = "sn") String sn) {
        logger.info("new user connecting, bid is : " + bid + ", sessionid is " + session.getId());
        this.session = session;
        sessionPool.put(bid, session);
        sessionIds.put(session.getId(), bid);
        if(snMaps.containsKey(sn)) {
            Set<String> ids = snMaps.get(sn);
            ids.add(bid);
        } else {
            Set<String> ids = new HashSet<>();
            ids.add(bid);
            snMaps.put(sn, ids);
        }
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
    public void onClose(@PathParam(value = "bid") String bid, @PathParam(value = "sn") String sn) {
        logger.info("close connect, bid is : " + bid + ", sessionid is " + session.getId());
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
        Set<String> ids = snMaps.get(sn);
        for (String id : ids) {
            if(bid.equals(id)) {
                ids.remove(id);
                break;
            }
        }
        if(ids.size() == 0) {
            snMaps.remove(sn);
        }
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
     * @param sn 设备编号
     */

    public static void sendMany(String msg, String sn) {
        Set<String> persons = snMaps.get(sn);
        if(null != persons) {
            logger.info("need send message to sn : " + sn + ", count is :" + persons.size());
            for (String bid : persons) {
                sendMessage(msg, bid);
                logger.info("start send message : " + msg + ", bid is : " + bid + ", sessionid is : " + sessionPool.get(bid).getId());
            }
        }
    }
}