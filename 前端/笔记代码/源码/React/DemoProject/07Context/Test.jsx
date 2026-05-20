import TestContext from './TestContext'
import A from './A'
import B from './B'

const Test=()=>{
    return (
        <div>
            {/* 使用Context.Provider标签包裹子组件，子组件及其后代组件都可以访问该Context */}
            <TestContext.Provider value={'aaa'}>
                <A />  {/* B组件中得到的context结果为aaa */}
                <TestContext.Provider value={'bbb'}>
                    <B />  {/* B组件中得到的context结果为bbb */}
                </TestContext.Provider>
            </TestContext.Provider>
        </div>
    );
};
export default Test;