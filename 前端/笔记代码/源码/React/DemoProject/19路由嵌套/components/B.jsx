
import { Route, useRouteMatch } from "react-router-dom/cjs/react-router-dom";
import C from './C';
const B=()=>{

    const match=useRouteMatch();

    return (
        <>
            <div>这是组件B</div>

            {/* 
                也可以将Route嵌套在指定的组件内，效果基本是一样的
            */}
            <Route path={`${match.path}/C`}>
                <C></C>
            </Route>
        </>
    );
};

export default B;