import { useRef, useState } from "react";
import Test from "./conponents/Test";



const App=()=>{
    
    const [count,setCount]=useState(0);
    const myRef=useRef();
    const appInputRef=useRef();

    const clickHandler=()=>{
        setCount(
            (prevState)=>{
                return prevState+1;
            }
        );
        myRef.current.setValue(appInputRef.current.value);
    };

    

    return (
        <div>
            <div>同步次数:{count}</div>
            <input type="text" ref={appInputRef}/>
            <button onClick={clickHandler}>点我使下面的输入框与我方输入框同步</button>
            <button onClick={()=>{console.log(myRef.current);}}>得到App组件内绑定了Test组件的的ref的值</button>
            <hr />
            <Test ref={myRef}></Test>
            
        </div>
    );
}

export default App;