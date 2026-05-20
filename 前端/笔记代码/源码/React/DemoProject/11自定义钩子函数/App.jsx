

import useTest from "./hooks/useTest";

const App=()=>{
    const {count,decreaseCount,addCount}=useTest();

    const decreaseHandler=()=>{
        decreaseCount(1);
    };
    const addHandler=()=>{
        addCount(1);
    };

    return (
        <div>
            <div style={{display:'flex',justifyContent:'center',marginTop:30}}>
                <button onClick={decreaseHandler}>减少</button>
                <span>{count}</span>
                <button onClick={addHandler}>增加</button>
            </div>
        </div>
    );
};

export default App;