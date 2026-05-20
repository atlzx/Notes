package com.example.cloud.resp;

import lombok.Data;

@Data
public class ReturnData<T> {
    private Integer code;
    private String message;
    private T data;
    private long timeStamp;

    public ReturnData() {
        this.timeStamp = System.currentTimeMillis();
    }

    public static <T> ReturnData<T> ok(T data){
        ReturnData<T> returnData=new ReturnData<>();
        returnData.setCode(ReturnCodeEnum.RC200.getCode());
        returnData.setMessage(ReturnCodeEnum.RC200.getMessage());
        returnData.setData(data);
        return returnData;
    }


    public static <T> ReturnData<T> fail(Integer code){
        ReturnData<T> returnData=new ReturnData<>();
        returnData.setCode(ReturnCodeEnum.getReturnCodeEnumV1(code).getCode());
        returnData.setMessage(ReturnCodeEnum.getReturnCodeEnumV1(code).getMessage());
        returnData.setData(null);
        return returnData;
    }
}
