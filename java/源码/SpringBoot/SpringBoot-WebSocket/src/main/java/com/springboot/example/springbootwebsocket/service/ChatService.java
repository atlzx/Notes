package com.springboot.example.springbootwebsocket.service;

import com.springboot.example.springbootwebsocket.mapper.UserDao;
import com.springboot.example.springbootwebsocket.pojo.User;
import com.springboot.example.springbootwebsocket.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatService {
    @Resource
    private UserDao userDao;
    public List<User> getFriends(String userId){
        List<User> userFriends = userDao.getUserFriends(userId);
        log.info(userId);
        for(User user:userFriends){
            user.setUserPassword(null);
        }
        updateRedisFriendsStatus(userId,userFriends);
        return userFriends;
    }

    public void updateRedisFriendsStatus(String userId,List<User> friends){
        RedisUtil.update(userId,"friends",friends);
    }


}
