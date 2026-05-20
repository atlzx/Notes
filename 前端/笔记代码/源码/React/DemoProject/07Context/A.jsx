import {useContext} from 'react'
import TestContext from './TestContext'

// 方法一：简易方法(使用钩子函数得到Context)

const A=()=>{
    const context=useContext(TestContext);
    return (
        <div>{context}</div>
    );
};

export default A;