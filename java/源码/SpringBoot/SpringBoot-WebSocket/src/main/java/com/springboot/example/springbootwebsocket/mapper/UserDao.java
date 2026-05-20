package com.springboot.example.springbootwebsocket.mapper;

import com.springboot.example.springbootwebsocket.pojo.User;

import java.util.List;

public interface UserDao {
    int addUser(List<User> users);

    List<User> getAllUser();

    User getUserByNameAndPassword(String userName,String password);

    List<User> getUserFriends(String userId);

}
