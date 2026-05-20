package com.springboot.example.springbootthymeleaf.things;

import com.springboot.example.springbootthymeleaf.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Something {

    public ServerResponse handle1(ServerRequest request) throws Exception {
        log.info("get请求处理");
        log.info("得到request相关参数" + request.pathVariables());
        return ServerResponse.ok().body("aaa");
    }

    public ServerResponse handle2(ServerRequest request) throws Exception {
        log.info("post请求处理");
        List<Person> list = new ArrayList<>(Arrays.asList(new Person("1", "lzx", 20, "aaa"), new Person("2", "abc", 20, "nb")));
        return ServerResponse.ok().body(list);
    }
}
