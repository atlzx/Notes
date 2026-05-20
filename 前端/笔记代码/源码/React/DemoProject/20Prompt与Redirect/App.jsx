
import {Route,NavLink,Redirect} from "react-router-dom/cjs/react-router-dom";
import A from "./components/A";
import B from "./components/B";
import D from "./components/D";

const App=()=>{
    return (
        <div>
            <ul>
                <li><NavLink to="/A">跳转到A</NavLink></li>
                <li><NavLink to="/A/B">跳转到B</NavLink></li>
                <li><NavLink to="/A/B/C">跳转到C</NavLink></li>
            </ul>
            
            
            

            <Route path="/A">
                <A></A>
                {/* 
                    可以直接将Route嵌套在Route里面
                    不能设置exact，因为设置exact之后会导致父标签不匹配从而不会加载该组件
                */}
                <Route path="/A/B">
                    <B></B>
                </Route>
            </Route>
            {/* 
                Redirect标签用来进行重定向，下面的意思是如果访问/E就重定向到/A/B，如果不写from属性那么只要访问Redirect组件所在的组件，就会重定向到指定路径下
                还有一个push属性，可以变重定向的模式:不写push时，重定向与history的replace类似，会覆盖本次历史记录。写了以后会类似history的push，在本次历史记录后添加记录
                但写了push可能会导致重定向后回退再次跑到该组件，再次触发重定向条件，就这样一直循环
            */}
            <Redirect from={'/E'} to={"/A/B"}></Redirect>
            <Route path="/D"><D></D></Route>
        </div>
    );
};
export default App;