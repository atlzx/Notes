package com.springboot.example.springbootwebsocket.controller;

import com.springboot.example.springbootwebsocket.entity.Result;
import com.springboot.example.springbootwebsocket.pojo.User;
import com.springboot.example.springbootwebsocket.service.ChatService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@Slf4j
@CrossOrigin
public class ChatController {
    @Resource
    private ChatService chatService;
    @PostMapping("/friends")
    public Result getFriends(@RequestBody Map<String,String> map){
        String userId = map.get("userId");
        log.info(map.toString());
        List<User> friends = chatService.getFriends(userId);

        return Result.getResult(friends,Result.SUCCESS_STATUS_CODE,Result.DEFAULT_SUCCESS_MESSAGE,null);
    }

    @PostMapping("user/{userId}/{targetId}")
    public Result getChatHistory(@PathParam(value = "userId") String userId,@PathParam("targetId") String targetId){
//        chatService.getChatHistory();
        return Result.getResult(null,Result.LOGIC_ERROR_STATUS_CODE,Result.DEFAULT_ERROR_MESSAGE,null);
    }
}
