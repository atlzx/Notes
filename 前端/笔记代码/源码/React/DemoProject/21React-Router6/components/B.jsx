
import {useMatch,useLocation,useNavigate} from "react-router-dom";
const B=()=>{

    // useMatch钩子函数取代了useRouteMatch钩子函数，它用来判断传入的匹配字符串是否与当前url相匹配，如果不匹配则返回null。如果匹配返回match对象
    const match1=useMatch('/A');
    const match2=useMatch('/B');
    const location=useLocation();  // useLocation并未发生变化
    const nav=useNavigate();  // useNavigate用来得到一个用于跳转的函数，该函数nav(to,obj)接收两个参数

    console.log(match1);
    console.log(match2);
    console.log(location);
    console.log(nav);  // 这里输出可以发现得到的nav是一个函数


    const clickHandler=()=>{
        // nav函数默认使用push的方式添加历史记录
        // 如果想修改，可以在函数接收的第二个参数内传入一个replace:true属性
        nav(
            '/C',
            {
                replace:true
            }
        );
    };

    return (
        <>
            <div>
                这是组件B
            </div>
            <button onClick={clickHandler}>点我跳转到C组件</button>
        </>
    );
};

export default B;