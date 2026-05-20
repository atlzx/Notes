
import { useCallback, useState } from "react";
import A from "./A";


const App=()=>{

    const [count,setCount]=useState(1);
    console.log('父组件渲染了');

    // useCallback钩子函数可以缓存该函数，直到其监听的数组对象内的元素发生了变化
    // 传入空数组时，useCallback仅会在组件第一次渲染时被调用，如果不写该空数组，那么每次组件渲染时useCallback都会调用，那就和没写没什么区别
    const testFunction=useCallback(
        ()=>{
            console.log('函数执行了');
        },
        []
    );

    return (
        <div>
            <div style={{display:'flex',justifyContent:'center',marginTop:30}}>
                <button onClick={()=>{setCount(count-1);}}>减少</button>
                <span>{count}</span>
                <button onClick={()=>{setCount(count+1);}}>增加</button>
            </div>
            {/* 
                在不写useCallback的前提下，直接向子组件传递函数对象会使memo失效，因为父组件每次渲染时都会重新创建函数，并将该函数对象赋值给该变量
                也就是说，每次父组件渲染时，表示该函数的变量其指向的内存地址都不一样
            */}
            <A count={count%4==0} testFunction={testFunction}></A>
        </div>
    );
};

export default App;