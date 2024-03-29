
import {createApi,fetchBaseQuery} from '@reduxjs/toolkit/query/react';


export const testApi=createApi(
    {
        // reducerPath用来指定该类的唯一标识，主要用来在创建store时指定action的type属性，如果不指定默认为api
        reducerPath:'testApi',
        // baseQuery用来设置发送请求的工具,而fetchBaseQuery是RTK提供的封装过的fetch
        baseQuery:fetchBaseQuery(
            {
                baseUrl:'https://api.uomg.com/api/'  // 指定默认通用的url
            }
        ),
        // endpoints对象用来配置我们请求时的端点
        // 端点就是一类功能中的每一个具体功能
        endpoints:(build)=>{
            return {
                // 该函数的返回值对象内的每一个属性都是createApi最终为我们生成的请求数据的钩子函数
                // 如果是查询操作那么使用build.query()方法，如果是增删改操作那么使用build.mutation()方法
                getInfo:build.query(
                    {
                        query:()=>{
                            return 'rand.qinghua?format=json';  // 这里返回的字符串会和上面的baseUrl字符串拼接起来形成一个完整的请求路径
                        }
                    }
                )
            };
        }
    }
);

// 生成的api对象内就包含了其自动生成的钩子函数
export const {useGetInfoQuery}=testApi;