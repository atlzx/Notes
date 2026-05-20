
import { Route, useRouteMatch, Prompt } from "react-router-dom/cjs/react-router-dom";
import C from './C';
const B=()=>{

    const match=useRouteMatch();

    return (
        <>
            <div>这是组件B</div>

            <Prompt message={'确定要进行跳转吗'}></Prompt>

            {/* 
                也可以将Route嵌套在指定的组件内，效果基本是一样的
            */}
            <Route path={`${match.path}/C`} exact>
                <C></C>
            </Route>
        </>
    );
};

export default B;