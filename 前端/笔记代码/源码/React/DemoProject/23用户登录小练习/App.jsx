

import Layout from './components/Layout';
import AuthPage from './pages/AuthPage';
import HomePage from './pages/HomePage';
import InfoPage from './pages/InfoPage';
import Check from './components/Check';
import {Routes,Route} from 'react-router-dom';
import { useAutoLogOut } from './hooks/useAutoLogOut';



const App=()=>{

    useAutoLogOut();  // 使用自定义钩子来使App组件尽量简洁直观，该钩子函数负责进行用户自动登出的判断与执行

    return (
        /* 
            使用自定义组件来保证App组件内仅需要写路由和Redux的一些相关配置
            Layout组件负责将这些路由会发生变化的可变组件和固定组件拼接在一起
         */
        <Layout>
            <Routes>
                <Route path='/' element={<HomePage />}></Route>
                <Route path='/login' element={<AuthPage />} />
                <Route path='/info' element={<Check><InfoPage /></Check>}></Route>
            </Routes>
        </Layout>

    );
};
export default App;