import { useEffect,useInsertionEffect, useLayoutEffect, useRef } from "react";




const App=()=>{

    // useEffect最后执行
    // useEffect是官方推荐的写法，理由是它性能比较高
    useEffect(
        ()=>{
            console.log('useEffect执行');
            console.log(ref);
        }
    );

    // useLayoutEffect第二个执行
    useLayoutEffect(
        ()=>{
            console.log('useLayoutEffect执行');
            console.log(ref);
            // 更改为红色，由于它是在DOM发生变化后而组件挂载前执行的，因此不会出现闪一下的情况
            // 如果是useEffect，可能会出现闪一下的情况
            // React18针对useEffect进行了优化，它的执行时机可能会变得不固定，因此React18后两者之间的差别很小
            ref.current.style.backgroundColor='red';  
        }
    );

    // useInsertionEffect最先执行
    useInsertionEffect(
        ()=>{
            console.log('useInsertionEffect执行');
            console.log(ref);  // 打印的ref为undefined,因为它执行时DOM还未发生改变
        }
    );

    const ref=useRef();


    return (
        <div style={{backgroundColor:'#ccc',height:100,width:100}} ref={ref}>
            呃呃
        </div>
    );

}

export default App;