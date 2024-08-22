
import { getAxiosTemplate } from "./axiosUtil";

export const getUserStatus=async ()=>{
    const instance = getAxiosTemplate();
    const {data}=await instance.request(
        {
            url:'/info/userInfo',
            method:'get'
        }
    );
    return data;
};