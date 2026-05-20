import { useState } from "react";
import React from "react";

const A=(props)=>{
    const [countState,setCountState]=useState(1);
    console.log('子组件重新渲染了')
    return (
        <div style={{display:'flex',justifyContent:'center',marginTop:15}}>
            <button onClick={()=>{setCountState(countState-1);}}>减少</button>
            <span>{countState}</span>
            <button onClick={()=>{setCountState(countState+1);}}>增加</button>
            <br />
            {props.judge&&<span>父组件的值能够被4整除了</span>}
        </div>
    );
};

export default React.memo(A);