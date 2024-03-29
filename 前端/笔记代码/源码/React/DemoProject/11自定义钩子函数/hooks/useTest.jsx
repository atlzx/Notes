import { useState } from "react";


// 自定义的钩子函数前缀需要为useXxx格式
const useTest=()=>{
    // 自定义的钩子函数内部可以调用其他的钩子函数
    const [count,setCount]=useState(1);
    const addCount=(number)=>{
        setCount(count+number);
    };
    const decreaseCount=(number)=>{
        setCount(count-number);
    };
    // 我们自定义的钩子函数可以把想返回的值返回，以向外暴露钩子函数内部的结构
    return {count,addCount,decreaseCount};
}
export default useTest;