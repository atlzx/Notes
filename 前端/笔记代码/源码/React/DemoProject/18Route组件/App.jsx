
import {Route,NavLink} from "react-router-dom/cjs/react-router-dom";
import A from "./components/A";
import B from "./components/B";
import C from "./components/C";
import D from "./components/D";
import E from "./components/E";
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
            <Route 
                path="/C/:id" /* :id表示指定一个属性为id的参数，该参数会被传入params内。它的匹配机制是匹配 /C/xxxx的路径 */
                // 使用render可以向组件传入参数，但是render并不会自动帮助我们将location、match、history三个属性传入组件，因此它向回调函数传入了一个带有这三个属性的参数来供我们使用
                render={
                    (renderProps)=>{
                        return (
                            <C match={renderProps.match} history={renderProps.history} location={renderProps.location} other={'aa'}/>
                        );
                    }
                }
                exact
            />
            {/* 
                还可以使用children的方式来映射组件，该方式也可以进行给组件传递额外的参数
                children属性有两种写法:
                    直接嵌套在Route组件内
                    作为Route组件的children属性存在（ESlint的规则并不支持这种做法，虽然它可以运行）
                这两种方法都可以指定两种实现方式:
                    使用回调函数,与render一样，但是回调函数返回的结果不管是否匹配都会直接显示
                    直接写一个组件，组件可以通过React-Router提供的钩子函数来获得match、history、location等参数
                使用children的方式可以通过钩子函数获得属性
            */}
            <Route 
                exact
                path="/D"
                children={
                    ()=>{
                        return (
                            <D></D>
                        );
                    }
                }
            />

            <Route exact path="/E">
                <E></E>
            </Route>
        </>

    );
};
export default App;