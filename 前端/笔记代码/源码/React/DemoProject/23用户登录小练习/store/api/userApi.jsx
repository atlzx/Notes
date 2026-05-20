

import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'


// 该Api仅为一个摆设，因为没有提供对应的后端来与之交互
export const userApi=createApi(
    {
        reducerPath:'userApi',
        baseQuery:fetchBaseQuery(
            {
                baseUrl:'http://localhost:1337/api/'
            }
        ),
        endpoints:(build)=>{
            return {
                login:build.mutation(
                    {
                        query:(user)=>{
                            return {
                                url:'auth/local/register',
                                method:"post",
                                body:user,
                            }
                        }
                    }
                ),
                register:build.mutation(
                    {
                        query:(user)=>{
                            return {
                                url:'auth/local',
                                method:'post',
                                body:user
                            }
                        }
                    }
                )
            };

        }
    }
);