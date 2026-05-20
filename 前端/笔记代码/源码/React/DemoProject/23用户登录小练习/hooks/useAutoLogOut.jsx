import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { logOut } from "../store/reducers/userReducer";


// 该钩子函数负责进行登出判断与操作
export const useAutoLogOut=()=>{

    const dispatch=useDispatch();
    const user=useSelector(
        (state)=>{
            return state.user;
        }
    );

    useEffect(
        ()=>{
            // 将登出时间与当前时间相减来判断是否登陆已过期
            const time=user.logOutTime-Date.now();
            // 如果相差时间过短(小于1s)，那么登出
            if(time<1000){
                dispatch(logOut());
            }
            // 如果相差还很多，那么设置一个定时器，到达time时间后直接登出
            const ID=setTimeout(
                ()=>{
                    dispatch(logOut());
                },
                time
            );
            return ()=>{
                clearTimeout(ID);  // 当监听的数据变化时，需要清除上一个useEffect设置的定时器
            };
        },
        [user]  // 使用useEffect监听user的变化，当其变化时也同步更新此定时器
    );
};

