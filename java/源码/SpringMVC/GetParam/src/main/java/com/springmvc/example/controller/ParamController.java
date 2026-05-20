package com.springmvc.example.controller;

import com.springmvc.example.pojo.Person;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/param")
@ResponseBody
@Controller
public class ParamController {


    @RequestMapping(value = "/test1")

    public String getJSONParam(@RequestBody Person person){
        return person.toString();
    }

    @GetMapping(value = "/test2")

    public String getCookieInfo(@CookieValue(value = "test")String value){
        return value;
    }

    @RequestMapping(value = "/test3")
    public String prevSessionTest(@RequestParam(value = "str")String str, HttpSession session){
        session.setAttribute("test",str);
        return "OK";
    }

    @GetMapping(value = "/test4")
    public String getSessionValue(@SessionAttribute(value = "test") String t){
        return t;
    }


    @GetMapping(value = "/test5")
    public String getHeaderValue(@RequestHeader("Host") String value){
        return value;
    }
}
