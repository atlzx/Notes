import { useReducer } from "react";

// 为了避免每次渲染时都会将该函数对象重新创建一遍，一般都将该对象放在组件外面，这样可以提高渲染效率
// reduce函数接收两个参数，第一个参数是之前的State值，第二个参数由派发器传入，是我们想告诉函数的一些信息所组成的对象
const reduce=(prevState,action)=>{
    switch(action.mode){
        case 'add':return prevState+1;
        case 'sub':return prevState-1;
        default:return prevState;  // 为了避免出现未预料到的情况，可以使用default来将其它情况统一返回原值
    }
};
// init函数用来进行初始值的初始化运算，其返回值将作为State变量的返回值
// 这里initialArg指定的是1，但返回的是initialArg+2,因此最终呈现的countState值为3
const init=(initialArg)=>{
    return initialArg+2;
}


const App=()=>{

    // useReducer接受三个参数，第一个参数是回调函数，用来整合对该State变量的不同操作
    // 第二个参数是默认初始值，指定State变量初始值
    // 第三个参数可选，它会根据第二个参数的值进行运算，并返回运算结果，返回值将作为State变量的初始值
    // 函数返回一个数组对象，对象有两个参数，第一个参数即为State值，第二个参数返回的是一个State修改的派发器，各函数通过指定该派发器来实现指定操作
    const [countState,countDispatcher]=useReducer(reduce,1,init);

    const addHandler=()=>{
        // 实现函数通过调用该State变量的派发器来对State对象进行操作
        countDispatcher({mode:'add'});
    };

    const subHandler=()=>{
        countDispatcher({mode:'sub'});
    };
    
    return (
        <div>
            <button onClick={subHandler}>减少</button>
            <span>{countState}</span>
            <button onClick={addHandler}>添加</button>
        </div>
    )   
};

export default App;