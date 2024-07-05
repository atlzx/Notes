package com.example.cloud.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@AllArgsConstructor
@Getter
@Slf4j
public enum ReturnCodeEnum {
    /**操作失败**/
    RC999(999,"操作XXX失败"),
    /**操作成功**/
    RC200(200,"success"),
    /**服务降级**/
    RC201(201,"服务开启降级保护,请稍后再试!"),
    /**热点参数限流**/
    RC202(202,"热点参数限流,请稍后再试!"),
    /**系统规则不满足**/
    RC203(203,"系统规则不满足要求,请稍后再试!"),
    /**授权规则不通过**/
    RC204(204,"授权规则不通过,请稍后再试!"),
    RC400(400,"请求失败"),
    /**access_denied**/
    RC401(401,"匿名用户访问无权限资源时的异常"),
    /**access_denied**/
    RC403(403,"无访问权限,请联系管理员授予权限"),
    RC404(404,"404页面找不到的异常"),
    RC408(408,"请求超时"),
    RC429(429,"请求过于频繁，请稍后再试"),
    /**服务异常**/
    RC500(500,"系统异常，请稍后重试"),
    RC375(375,"数学运算异常，请稍后重试"),

    INVALID_TOKEN(2001,"访问令牌不合法"),
    ACCESS_DENIED(2003,"没有权限访问该资源"),
    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    BUSINESS_ERROR(1004,"业务逻辑异常"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

    private final Integer code;
    private final String message;

    @Override
    public String toString() {
        return "ReturnCodeEnum{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            '}';
    }

    // 根据code得到返回值，传统for遍历方式
    public static ReturnCodeEnum getReturnCodeEnumV1(Integer code){
        // 枚举类自带values方法，可以得到本类的全部枚举对象组成的数组
        for (ReturnCodeEnum item : ReturnCodeEnum.values()) {
            if(item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }

    // 根据code得到返回值，操蛋的lambda表达式遍历方式
    public static ReturnCodeEnum getReturnCodeEnumV2(Integer code){
        // 先使用Arrays.stream方法来得到stream对象
        return Arrays
            .stream(ReturnCodeEnum.values())
            // 使用filter方法来得到符合条件的值
            .filter(
                (item)->{
                    return item.getCode().equals(code);
                }
            )
            // 得到过滤之后的第一条数据
            .findFirst()
            // 如果没有返回个null
            .orElse(null);
    }

    public static void main(String[] args) {
        log.info("{}", getReturnCodeEnumV1(200));
        log.info("{}", getReturnCodeEnumV1(205));
        log.info("{}", getReturnCodeEnumV2(200));
        log.info("{}", getReturnCodeEnumV2(205));
    }

}
