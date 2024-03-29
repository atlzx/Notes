import { useDebugValue, useEffect } from "react";


const useTest=()=>{

    useDebugValue('哈哈');  // 给自定义钩子定义一个标签，该标签在浏览器的react插件内可见

    useEffect(
        ()=>{
            console.log('函数执行');
        },
        []
    );
};

export default useTest;
