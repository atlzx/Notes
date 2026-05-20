
import { Route } from "react-router-dom/cjs/react-router-dom";
import A from "./components/A";
import B from "./components/B";

const App=()=>{
    return (
        <>
            <h1>欢迎</h1>
            <Route path="/" component={A} exact/>
            {/* 使用exact属性以规定path的完整匹配模式，如果不指定，那么默认就是匹配URL是否以该字符串开头，如果开头那么直接显示 */}
            <Route path="/B" component={B} exact></Route>
        </>

    );
};
export default App;