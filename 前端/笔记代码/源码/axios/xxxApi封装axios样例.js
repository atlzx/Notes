import request from '@/request/request.js';

const login=(url,data,config)=>{
    return request.post(url,data,config);
};

export {login};