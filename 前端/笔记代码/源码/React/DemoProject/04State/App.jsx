
import {useState} from 'react'


const App=()=>{

    // 利用解构得到useState返回的State初始值与设置函数
    // 钩子函数必须写在函数组件内部
    let [obj,setCount]=useState({name:'lzx',age:18,desc:'我'});


    // 增加
    const add=()=>{

        /*
            setCount的执行是异步的，他会先进入消息队列，等待调用栈中的操作全部执行完后再执行
            这会导致问题:如果短时间内调用栈操作尚未完成，而用户点击了多次按钮，那么这几次setCount设置的值都会为2
            因为调用栈操作还没执行完，前面的setCount函数就不会执行，这时再次将后面的setCount加入消息队列，它们设置的值都是一样的，因为全都是读取的当前的count的值
            为了避免这一问题，可以通过向setCount传递回调函数进行设置，回调函数的返回值将成为count下次渲染后的新值，同时回调函数取得的count值永远都是State中最新的，而不是当前的

        */

        setCount(
            // 给setCount传递一个回调函数作为参数，可以有效避免异步问题
            (stateObj)=>{
                
                return {...stateObj,name:'张三'};
            }
        );
    }

    // 减少
    const decrease=()=>{
        setCount(
            // 回调函数接收当前State存储的最新值，而不是目前网页中存储的值
            (stateObj)=>{
                // 对对象进行浅拷贝，并修改其中的name属性
                // 如果不进行浅拷贝直接修改原对象，不会发生任何变化，因为对象之间比较的是内存地址，react在比较后不会认为该变量发生了变化，就不会改变
                return {...stateObj,age:20};
            }
        );
    }


    return (
        <div>
            <div>{obj.name}---{obj.age}---{obj.desc}</div>
            <div>
                <button onClick={decrease}>-</button>
                <button onClick={add}>+</button>
            </div>
        </div>
    );
    
}

export default App;