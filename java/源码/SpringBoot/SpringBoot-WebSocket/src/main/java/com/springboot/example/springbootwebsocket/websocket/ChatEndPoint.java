package com.springboot.example.springbootwebsocket.websocket;

import com.springboot.example.springbootwebsocket.entity.WebSocketMessage;
import com.springboot.example.springbootwebsocket.utils.CommonUtil;
import com.springboot.example.springbootwebsocket.utils.JsonUtil;
import com.springboot.example.springbootwebsocket.utils.WebSocketUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{userId}")
@Slf4j
@Component
public class ChatEndPoint {
    private static final Map<String, Session> onLineUsers = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("userId") String userId) {
        // onOpen要做的有两件事，一件是将连接的该用户加入到在线序列中去，另一件事是向所有用户广播该用户已在线
        try {
            // 将session对象加入到在线用户的map集合中
            onLineUsers.put(userId, session);
            // 得到向所有在线成员广播的系统信息
            String message = "online";
            // 向所有成员进行广播
            broadcastAllUserMessage(message);
            log.info("连接成员:{}",onLineUsers.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    @OnMessage
    public void onMessage(String message) throws Exception{
        WebSocketMessage webSocketMessage = JsonUtil.jsonToEntity(message, WebSocketMessage.class);
        Session session = onLineUsers.get(webSocketMessage.getToUser());
        log.info("解析完成");
        log.info("{}",onLineUsers.toString());
        session.getBasicRemote().sendText(JsonUtil.entityToJson(webSocketMessage));
        onLineUsers.get(webSocketMessage.getFromUser()).getBasicRemote().sendText(WebSocketUtil.getSystemMessage("success",webSocketMessage.getFromUser(),System.currentTimeMillis()));
    }


    @OnClose
    public void onClose(CloseReason reason,Session session){
        log.info(reason.toString());
    }

    @OnError
    public void onError(Throwable e){
        log.info(e.toString());
    }

    private void broadcastAllUserMessage(String msg) throws Exception{
        Long timeStamp=System.currentTimeMillis();
        // 遍历每个在线用户，向它们的客户端发送消息
        for (Map.Entry<String, Session> entry : onLineUsers.entrySet()) {
            Session userSession = entry.getValue();
            log.info("{}",onLineUsers.toString());
            userSession.getBasicRemote().sendText(WebSocketUtil.getSystemMessage(msg,entry.getKey(),timeStamp));
        }
    }


    private void updateChatHistory(){

    }
}
