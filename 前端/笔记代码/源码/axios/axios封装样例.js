import axios from "axios";

// 创建一个request对象
const request=axios.create(
    {
        baseURL:'http://localhost:9090'
    }
);


// 使用拦截器
request.interceptors.request.use(
    // 第一个参数是发送请求前要进行的对配置的操作
    (config)=>{
        console.log(config);
        return config;
    },
    // 第二个参数是请求操作出现异常时要进行的操作
    (error)=>{
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    // 第一个参数是得到响应时提前于then方法所对响应进行的相关操作,当HTTP状态码是2xx时,此方法都会执行
    (resp)=>{
        console.log(resp);
        return resp;
    },
    // 第二个参数是响应出错时的相关操作,当HTTP状态码是2xx以外的状态码时时,此方法都会执行
    (error)=>{
        return Promise.reject(error);
    }
);

export default request;