
import {useState, useTransition } from "react";
import StuList from "./conponents/StuList";



const App=()=>{

    const [keyword,setKeyWord]=useState('');
    const [value,setValue]=useState('');
    // 使用useTransition与useDeferredValue情况类似，都会渲染两次，第一次渲染都不会改变state值
    const [ispending,startTransition]=useTransition();

    const changeHandler=(e)=>{
        setKeyWord(e.target.value);
        startTransition(
            ()=>{
                setValue(e.target.value);
            }
        );
    };
    return (
        <div>
            {/* 
                必须要input和stuList全部渲染完毕后，组件才能挂载到DOM上
            */}
            <input type="text" value={keyword} onChange={changeHandler}/>
            {/* 
                当ispending为true时说明正在执行，因此为false时说明已经执行完毕了，此时再显示组件。
                该方法可以在第一次渲染时跳过对StuList的无用渲染，使第一次渲染时可以快速地更新input的变化

            */}
            {!ispending&&<StuList keyword={value}></StuList>}
        </div>
    );
}

export default App;