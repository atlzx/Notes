import { useMemo, useState } from "react";


const fun=(a,b)=>{
    console.log('函数执行了');
    return a+b;
}



const App=()=>{
    
    const [count,setCount]=useState(1);

    // 这里为了省事，直接使用的let声明的变量。使用let声明的变量，组件无法记住它们，因此每次组件渲染时都会被重新创建并初始化
    let a=1;
    let b=2;
    // 如果count能够被10整除，就让它与a相加
    // 在count=10和11的时候都会更新缓存，因为count=10的时候满足条件导致a变成11
    // count=11的时候不满足条件，但是上一次记住的缓存中函数的执行参数内a还是11而不是1，因此依然会更新
    if(count%10===0){
        a+=count;
    }


    useMemo(
        ()=>{
            return fun(a,b);  // 回调函数需要返回函数执行的值，因为它缓存的是函数的执行结果而不是函数对象
        },
        [a,b]
    );

    const clickHandler=()=>{
        setCount(
            (prevState)=>{
                return prevState+1;
            }
        );
    }

    return (
        <div>
            <div>{count}</div>
            <button onClick={clickHandler}>点我更新</button>
        </div>
    );
}

export default App;