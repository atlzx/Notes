package com.example.cloud.controller;

import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.resp.ReturnData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    // 使用consul以后，只需要写想把请求转发到的微服务模块注册的名字即可(对应模块通过 spring.cloud.consul.discovery.service-name 配置指定的值)
    private final String PAY_URL="http://cloud-pay-service";
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/pay/add")
    public ReturnData addOrder(@RequestBody PayDTO payDTO){
        // 传递一般传三个参数即可，想把请求转发给的路径，请求参数，以及期待返回的对象Class类型
        ReturnData returnData = restTemplate.postForObject(PAY_URL + "/pay/add", payDTO, ReturnData.class);
        return returnData;
    }

    @DeleteMapping("/pay/delete/{id}")
    public ReturnData deleteOrder(@PathVariable("id") Integer id){
        // 路径参数应该放到url参数内，不应该写在uriVariables参数内
        restTemplate.delete(PAY_URL+"/pay/delete/"+id);
        return ReturnData.ok(1);
    }

    @GetMapping("/pay/get/{id}")
    public ReturnData getOrder(@PathVariable("id") String id){
        // 路径参数应该放到url参数内，不应该写在uriVariables参数内
        ReturnData returnData = restTemplate.getForObject(PAY_URL + "/pay/get/"+id, ReturnData.class);
        return returnData;
    }

    @PutMapping("/pay/update")
    public ReturnData updateOrder(@RequestBody PayDTO payDTO){
        restTemplate.put(PAY_URL+"/pay/update",payDTO);
        return ReturnData.ok(1);
    }

    @GetMapping("/pay/getAll")
    public ReturnData getAllOrder(){
        ReturnData returnData = restTemplate.getForObject(PAY_URL + "/pay/getAll", ReturnData.class);
        return returnData;
    }

    @GetMapping("/pay/get/info")
    public ReturnData getInfo(){
        ReturnData returnData = restTemplate.getForObject(PAY_URL+"/pay/get/info", ReturnData.class);
        return returnData;
    }
}
