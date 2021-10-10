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

import com.silence.robot.service.InstantMessagingService;
import com.silence.robot.utils.SpringContextHelper;
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

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static ConcurrentHashMap<String, WebSocketServer> webSocketServerConcurrentHashMap = new ConcurrentHashMap<>();


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static int onlineCount = 0;

    private static final Boolean lock = false;

    /**
     * 接收sid
     */
    private String sid;

    /**
     * 连接建立成功调用的方法
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        this.sid = sid;
        webSocketServerConcurrentHashMap.put(sid, this);
        logger.info("连接建立成功");
        addOnlineCount();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        //从集合中删除
        webSocketServerConcurrentHashMap.remove(this.sid);
        //在线人数减一
        subOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message){
        logger.info("收到来自窗口[{}]的信息[{}]",this.sid,message);
        InstantMessagingService instantMessagingService = SpringContextHelper.getBean(InstantMessagingService.class);
        String outMessage = instantMessagingService.handleMessage(message);
        if(outMessage != null){
            webSocketServerConcurrentHashMap.forEach((str,webSocketServer) -> webSocketServer.sendMessage(outMessage));
        }

    }

    /**
     *
     * @param session
     * @param e
     */
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

    /**
     * 群发自定义消息
     * @param message
     * @param sid
     */
    public static void sendInfo(String message, @PathParam("sid") String sid, String mineId){
        logger.info("推送消息到窗口{}，推送内容:{}",sid,message);

        webSocketServerConcurrentHashMap.forEach((str,webSocketServer) -> {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if("0".equals(sid) && !str.equals(mineId)){
                webSocketServer.sendMessage(message);
            }else if(str.equals(sid)){
                webSocketServer.sendMessage(message);
            }

        });

    }

    public static void addOnlineCount(){
        synchronized (lock){
            onlineCount++;
        }
    }

    public static void subOnlineCount(){
        synchronized (lock){
            onlineCount--;
        }
    }

    public static int getOnlineCount(){
        synchronized (lock){
            return onlineCount;
        }
    }



}