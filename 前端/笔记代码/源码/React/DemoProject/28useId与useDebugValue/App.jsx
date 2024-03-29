import { useId } from "react";
import useTest from "./hooks/useTest";




const App=()=>{

    const id=useId();  // 自动生成ID

    useTest();


    return (
        <>
            <label htmlFor={id}>点我啊</label>
            <input type="text" id={id} />
        </>

    );

}

export default App;