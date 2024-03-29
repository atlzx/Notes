
import {Route,NavLink} from "react-router-dom/cjs/react-router-dom";
import A from "./components/A";
import B from "./components/B";

const App=()=>{
    return (
        <div>


            <Route path="/A">
                <A></A>
                {/* 
                    可以直接将Route嵌套在Route里面
                */}
                <Route path="/A/B">
                    <B></B>
                </Route>
            </Route>
        </div>
    );
};
export default App;