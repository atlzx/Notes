
import { useDeferredValue, useMemo, useState } from "react";
import StuList from "./conponents/StuList";



const App=()=>{

    const [keyword,setKeyWord]=useState('');

    const value=useDeferredValue(keyword);  //  使用useDeferredValue进行处理，主要利用的是组件渲染两次和第一次渲染返回state的旧值的特性

    const changeHandler=(e)=>{
        setKeyWord(e.target.value);
    };

    // useMemo也可以作用于函数式组件，从而代行React.memo的作用
    // 接收的stuList可以通过插值表达式插入jsx中
    // 仅使用useDeferredValue是不够的，而且更严重了，之前只渲染一次，现在渲染两次了
    // 因此还需要使用useMemo或React.memo的缓存机制进行配合，才能发挥作用
    // 由于第一次渲染时传入的是旧值，因此缓存机制被触发，从而使第一次渲染时StuList并未渲染，因为传入的value是旧的state,使第一次渲染很快
    // 从而使得input呈现的值能很快地更新，而第二次渲染时，缓存机制无法产生作用了，因此StuList才被渲染
    // 但此时如果连续且快速的向输入框输入一些值时，依然会出问题，因为state更新的太过频繁导致缓存几乎无法发挥作用
    // 而且不管怎么说，最后StuList是一定要渲染的，该情况无法避免，因此效果其实并不是太好，只是起一个缓兵之计的作用
    const stuList=useMemo(
        ()=>{
            return <StuList keyword={value}/>;
        },
        [value]
    );

    return (
        <div>
            {/* 
                必须要input和stuList全部渲染完毕后，组件才能挂载到DOM上
            */}
            <input type="text" value={keyword} onChange={changeHandler}/>
            {stuList}
        </div>
    );
}

export default App;