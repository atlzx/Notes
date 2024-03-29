
import {Routes,Route} from 'react-router-dom';
import A from './components/A';
import B from './components/B';
import C from './components/C';
import D from './components/D';
import E from './components/E';
import Menu from './components/Menu';

const App=()=>{
    return (
        <div>

            <Menu />

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
                <Route path='/D/:id' element={<D />}>
                    {/*
                        通过Route组件的嵌套来使子组件在指定的URL被显示
                        由于匹配到E所在的路径时，D的路径就不会成功匹配，因此E组件就无法正常显示，因为它的父组件没有办法显示导致它没有地方可以显示
                        但组件内的结构是嵌套的，因此需要在组件D内插入Outlet组件
                        Outlet组件可以保证其路由嵌套的子组件可以在URL匹配时正常显示
                    */}
                    <Route path='E' element={<E />}></Route>
                </Route>
            </Routes>
        </div>
    );
};
export default App;