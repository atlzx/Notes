import { useZustandDemoStore } from "../store/ZustandStoreDemo";
import {useShallow} from 'zustand/shallow';

const ZustandDemo = (props) =>{
    // 读取时，如果要读取多个state，需要借助useShallow执行操作
    // useShallow接收一个回调函数，回调函数将能接收到store中全部state组成的对象（即set函数的返回的那个对象），回调函数的返回值将成为useXXXStore的返回值
    // 读取多个state时，当这些state有一个发生更改时，相关的组件将会触发重新渲染
    // 实际上，useZustandDemoStore提供了第二个参数，也是一个回调函数，它用来自定义新旧值的比较规则，回调函数返回true即表示state发生了变化
    const {test,status,setTest,setStatus,removeStatus}=useZustandDemoStore(
        useShallow(
            ({test,status,setTest,setStatus,removeStatus})=>({test,status,setTest,setStatus,removeStatus})
        )
    )

    /**
     * 也可以使用下面的方式直接拿到state对象:
     * const obj = useZustandDemoStore();
     * 但是这种方式会导致当store任意一个state发生变化时，都会触发组件的重新渲染，因此建议组件用哪个就拿哪个就行
     */

    return (
        <>
            <div>{test}</div>
            <div>{`${status}`}</div>
            <div>
                <button onClick={()=>{setStatus(true);setTest('Hello World');}}>点我更新值</button>
                <button onClick={()=>{removeStatus();}}>点我清除state</button>
            </div>
        </>
    );
};
export default ZustandDemo;