package com.springboot.example.springbootwebsocket.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.example.springbootwebsocket.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    private Integer isSystemMessage;
    private String fromUser;
    private String toUser;
    private String message;
    private Long timeStamp;
}
