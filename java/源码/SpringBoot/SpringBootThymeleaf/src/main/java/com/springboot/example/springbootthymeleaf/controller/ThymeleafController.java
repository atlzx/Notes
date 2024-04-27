package com.springboot.example.springbootthymeleaf.controller;

import com.springboot.example.springbootthymeleaf.pojo.Person;
import com.springboot.example.springbootthymeleaf.service.MyService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/th")
public class ThymeleafController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MyService myService;
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name, Model model, HttpSession session){
        model.addAttribute("message",name);
        model.addAttribute("utext","<h3>utext标签</h3>");
        model.addAttribute("text","<h3>utext标签</h3>");
        // 这里请求的是静态资源，默认请求静态资源前缀是那四个路径，我们这里有static文件夹，因此/static/会作为前缀与我们写的路径拼接在一起
        // 如果不写/，那么就不是/**开头的资源请求了，那么就不会走静态资源流程
        model.addAttribute("src1","/img/盒子模型.png");
        model.addAttribute("src2","/img/盒子模型.png");
        model.addAttribute("style","width:500px");
        model.addAttribute("show1",true);
        model.addAttribute("show2",false);
        model.addAttribute("str","str");
        model.addAttribute("name","lzx");
        model.addAttribute("switch",2);
        Map<String,String> map=new HashMap<>();
        map.put("aaa","test1");
        map.put("bbb","test2");
        map.put("ccc","test3");
        session.setAttribute("map",map);
        return "hello";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Person> list=new ArrayList<>(
            Arrays.asList(
                new Person("1","zkx",21,"玩Apex"),
                new Person("2","cdl",20,"努力学习"),
                new Person("3","sh",20,"玩小游戏"),
                new Person("4","tcy",20,"颠佬"),
                new Person("5","lzx",20,"摆烂"),
                new Person("6","wth",20,"rbq")
            )
        );
        model.addAttribute("list",list);
        return "list";
    }

    @GetMapping("/template")
    public String template(){
        return "useTemplate";
    }

    @GetMapping("/national")
    @ResponseBody
    public String national(ServletRequest request){
        Locale locale = request.getLocale();
        String message = messageSource.getMessage("message", null, locale);
        return message;
    }

    @GetMapping("/nationalTemplate")
    public String nationalTemplate(){
        return "national";
    }

    @GetMapping("/error")
    public String error() throws Exception{
        int a=1/0;
        throw new ArithmeticException("aaaa");
    }

    @GetMapping("/useRequest")
    public String useRequestAnywhere(){
        myService.useRequest();
        return "useRequest";
    }
}
