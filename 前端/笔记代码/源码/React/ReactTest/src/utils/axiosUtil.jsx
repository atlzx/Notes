import axios from "axios";
import { getJwt } from "./jwtUtil";




export const getAxiosTemplate=()=>{
    const instance=axios.create(
        {
            baseURL:'http://8.130.44.112:8080',
            method:'post',
            headers:{
                Authorization:getJwt(),
                'Content-Type':'application/json'
            },
            params:{},
            data:{},
            responseType:'json'
        }
    );
    return instance;
};