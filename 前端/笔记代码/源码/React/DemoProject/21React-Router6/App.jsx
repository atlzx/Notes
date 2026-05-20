
import {Routes,Route} from 'react-router-dom';
import A from './components/A';
import B from './components/B';
import C from './components/C';

const App=()=>{
    return (
        <div>
            {/* 
                React-Router6的Route组件必须写在Routes组件内才能发挥作用
            */}
            <Routes>
                {/* 
                    Route组件的render、children和component组件不再与组件挂钩，取而代之的是element属性
                    同时Route组件默认开启了exact
                */}
                <Route path='/' element={<A />}></Route>
                <Route path='/B' element={<B />}></Route>
                <Route path='/C' element={<C />}></Route>
            </Routes>
        </div>
    );
};
export default App;