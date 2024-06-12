package com.springboot.example.springbootwebsocket.service;

import com.springboot.example.springbootwebsocket.entity.WebSocketMessage;
import com.springboot.example.springbootwebsocket.utils.JSONUtil;
import com.springboot.example.springbootwebsocket.utils.SpringUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/chat/{userName}")
@Slf4j
@Component
public class ChatEndPoint {
    private static final Map<String, Session> onLineUsers = new HashMap<>();
    @Resource
    private JSONUtil jsonUtil;
    @Resource
    private LoginService loginService;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("userName") String userName) {
        log.info("" + jsonUtil);
        log.info(""+loginService);

        log.info(userName);
        // onOpen要做的有两件事，一件是将连接的该用户加入到在线序列中去，另一件事是向所有用户广播该用户已在线
        try {
            // 将session对象加入到在线用户的map集合中
            onLineUsers.put(userName, session);
            // 得到向所有在线成员广播的系统信息
            String message = "测试";
            // 向所有成员进行广播
            broadcastAllUserMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    @OnMessage
    public void onMessage(String msg) throws Exception{
        JSONUtil jsonUtil1=SpringUtil.getBean(JSONUtil.class);
        WebSocketMessage webSocketMessage = jsonUtil.jsonToEntity(msg, WebSocketMessage.class);
        Session session = onLineUsers.get("lzx");
        session.getBasicRemote().sendText(webSocketMessage.getMessage());
    }



    @OnClose
    public void onClose(Session session) throws Exception{

        onLineUsers.remove("lzx");
        broadcastAllUserMessage("下线");
    }

    private void broadcastAllUserMessage(String msg) throws Exception{
        // 遍历每个在线用户，向它们的客户端发送消息
        for (Map.Entry<String, Session> entry : onLineUsers.entrySet()) {
            Session userSession = entry.getValue();
            userSession.getBasicRemote().sendText(msg);
        }
    }
}
