package com.springmvc.example.controller;

import com.springmvc.example.pojo.People;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/param")
public class ParamController {

    // 使用通配符和@PathVariable注解示例
    // 测试接收路径参数
    @ResponseBody
    // @GetMapping作用的方法仅接收Get类型请求
    @GetMapping("/test1/{*page}")
    public String getParam1(@PathVariable(value = "page") String page){
        return page;
    }


    // 测试接收参数的一般情况
    @ResponseBody
    /*
        @RequestMapping注解可以用来指定映射
            作用在方法上时，Spring会将其value与其所在类上的对应注解的value像拼接得到完整路径，再与前端请求路径相匹配。如果类上没有注解，那么它的value就是完整路径
            method用来指定该方法可以接收的请求类型，可以直接指定枚举类RequestMethod中的属性值
     */
    @RequestMapping(value = "test2",method = {RequestMethod.GET,RequestMethod.POST})
    /*
        使用@RequestParam注解示例
            该注解可以得到param参数，如果传来的参数名与方法对应的参数名不同，可以使用value指定传来的前端参数名，并将该注解作用于其对应的方法参数上
            required可以指定该参数是否必须，默认是true，改为false为不必须，此时参数可以不传，不传不会报错
                defaultValue用来指定参数的默认值
     */
    public String getParam2(@RequestParam(value = "name") String name,@RequestParam(value = "age",required = false,defaultValue = "10") Integer age){
        return "name="+name+",age="+age;
    }

    @RequestMapping("test3")
    @ResponseBody
    // 测试List集合接收参数
    public String getParam3(@RequestParam(value = "key") List<String> list){
        System.out.println(list);
        return list.toString();
    }


    @RequestMapping("test4")
    @ResponseBody
    // 测试实体类接收参数
    public String getParam4( People people){
        return people.toString();
    }


}
