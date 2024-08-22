/* eslint-disable react/prop-types */
import { Navigate } from "react-router";
import { getJwt } from "../utils/jwtUtil";
import { useContext, useEffect } from "react";
import UserContext from "../context/UserContext";
import { getAxiosTemplate } from "../utils/axiosUtil";

const RouteAuth=({children})=>{
    // const userContext=useContext(UserContext);
    // 变量初始化
    // useEffect(
    //     ()=>{
    //         const initParam=async ()=>{
    //             let resp;
    //             const axiosInstance=getAxiosTemplate();
    //             console.log(userContext);
    //             // 如果直接通过输入地址跳转，可能会出现userStatus得不到的情况
    //             if(!userContext.userStatus){
    //                 console.log(1);
    //                 resp=await axiosInstance.request(
    //                     {
    //                         url:'/info/userInfo',
    //                         method:'get'
    //                     }
    //                 );
    //                 console.log(resp.data);
    //                 userContext.userStatus=resp.data.userStatus;
    //             }
                
    //         };
    //         initParam();
    //     }
    // );

    const jwt=getJwt();

    if(jwt){
        console.log('Jwt校验成功');
        return children;
    }else{
        console.log('Jwt校验失败');
        return <Navigate to={'/login'} replace/>
    }

    
    
    
};

export default RouteAuth;