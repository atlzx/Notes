
import React from 'react';
import {createRoot} from 'react-dom/client';
import App from './App.jsx';
import { Provider } from 'react-redux';
import {store} from './store/store.jsx';


const root=createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        {/* 
            提供Provider以让全部的组件都能够接收到store对象，从而进一步调用useSelector、useDispatcher等钩子函数
            这些钩子函数以及Provider组件是由react-redux提供的，并非由RTK提供
        */}
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);