

import {createRoot} from 'react-dom/client';
import {BrowserRouter} from 'react-router-dom';
import {store} from './store/index';
import { Provider } from 'react-redux';
import App from './App';

const root=createRoot(document.getElementById("root"));
root.render(
    <BrowserRouter>
        <Provider store={store}>
            <App></App>
        </Provider>
    </BrowserRouter>
);