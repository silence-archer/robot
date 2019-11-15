/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: WebSocketServer
 * Author:   silence
 * Date:     2019/11/8 22:17
 * Description: websocket服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈websocket服务〉
 *
 * @author silence
 * @create 2019/11/8
 * @since 1.0.0
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static ConcurrentHashMap<String, WebSocketServer> webSocketServerConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收sid
     */
    private String sid;

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        this.sid = sid;
        webSocketServerConcurrentHashMap.put(sid, this);
        sendMessage("连接成功");

    }

    @OnClose
    public void onClose(){
        webSocketServerConcurrentHashMap.remove(this.sid);
    }

    @OnMessage
    public void onMessage(Session session, String message){
        logger.info("收到来自窗口[{}]的信息[{}]",this.sid,message);
        webSocketServerConcurrentHashMap.forEach((str,webSocketServer) -> webSocketServer.sendMessage(message));
    }

    @OnError
    public void onError(Session session, Throwable e){
        logger.error("发生错误",e);
    }

    /**
     * 实现服务器主动推送消息给前端
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("通信连接失败",e);
        }
    }

}