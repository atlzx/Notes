
import {Route,NavLink } from "react-router-dom/cjs/react-router-dom";
import A from "./components/A";
import B from "./components/B";
import classes from './components/A.module.css'

const App=()=>{
    return (
        <>
            <h1>欢迎</h1>
            <ul>
                <li><NavLink to="/" activeClassName={classes.navLink} exact>查看A组件</NavLink></li>
                <li><NavLink to="/B" activeClassName={classes.navLink} exact>查看B组件</NavLink></li>
            </ul>
            <Route path="/" component={A} exact/>
            {/* 使用exact属性以规定path的完整匹配模式，如果不指定，那么默认就是匹配URL是否以该字符串开头，如果开头那么直接显示 */}
            <Route path="/B" component={B} exact></Route>
        </>

    );
};
export default App;