
import {configureStore} from '@reduxjs/toolkit'
import { testApi } from './testApi.jsx';

export const store=configureStore(
    {
        reducer:{
            [testApi.reducerPath]:testApi.reducer
        },
        /* 这个middleware即中间件的意思，它可以使我们请求的数据进入缓存 */
        middleware:(getDefaultMiddleware)=>{
            return getDefaultMiddleware().concat(testApi.middleware);
        }
    }
);