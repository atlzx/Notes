import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";


// Check组件是跳转页面是进行合法性校验的组件，它会检查用户的登陆状态是否为true，如果登陆状态不为true且想访问登录后才能访问的页面，会给其重定向到登陆界面
const Check=(props)=>{

    const {isLogin}=useSelector(
        (state)=>{
            return state.user;
        }
    );
    const location=useLocation();

    /* 
        如果已经登录了，那么继续跳转，如果未登录，那么重定向到登陆界面
        重定向时，可以向重定向的下一个标签页提供location对象的state属性，以便重定向的标签页调用
    */
    return isLogin?props.children:<Navigate to='/login' replace state={{prevLocation:location}} />;
};

export default Check;