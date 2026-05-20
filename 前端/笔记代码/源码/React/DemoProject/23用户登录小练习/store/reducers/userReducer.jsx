import { createSlice } from "@reduxjs/toolkit";


export const userReducer=createSlice(
    {
        name:'user',
        /* initialState也支持传入一个回调函数作为参数，回调函数的返回值将成为初始化的State值 */
        initialState:()=>{
            // 返回一个对象，并从本地的磁盘中读取值，以实现数据的持久化，避免刷新时数据丢失
            return {
                isLogin:localStorage.getItem("isLogin"),
                userName:localStorage.getItem("userName"),
                logOutTime:0
            };
        },
        reducers:{
            login:(state,action)=>{
                state.isLogin=true;
                state.userName=action.payload.userName;
                state.logOutTime=1000*60*60*24*7+Date.now();  // 此处设置其登出时间为一周以后
                // 登陆时将用户的一些信息传入localStorage中进行持久化存储
                localStorage.setItem("isLogin",true);
                localStorage.setItem("userName",state.userName);
                localStorage.setItem("logOutTime",state.logOutTime);  
            },
            // 这个register貌似根本就不应该写
            register:(state,action)=>{
                state.isLogin=false;
                state.userName=null;
            },
            logOut:(state,action)=>{
                // 登出时移除全部的数据
                state.isLogin=false,
                state.userName=null;
                localStorage.removeItem("isLogin");
                localStorage.removeItem("userName");
                localStorage.removeItem("storageTime");
            }
        }
    }
);

export const {login,register,logOut}=userReducer.actions;