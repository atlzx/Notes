import { configureStore } from "@reduxjs/toolkit";
import {userApi} from "./api/userApi";
import { setupListeners } from "@reduxjs/toolkit/query";
import {userReducer} from "./reducers/userReducer";


export const store=configureStore(
    {
        reducer:{
            [userApi.reducerPath]:userApi.reducer,
            user:userReducer.reducer
        },
        middleware:(getDefaultMiddleware)=>{
            return getDefaultMiddleware().concat(userApi.middleware);  // 执行缓存机制
        }
    }
);

setupListeners(userApi);   // 支持refetchOnFocus属性和refetchOnReconnect属性