import {createBrowserRouter} from 'react-router-dom';
import Login from '../pages/Login';
import Chat from '../pages/Chat';
import RouteAuth from './RouteAuth';
import { getUserStatus} from '../utils/userUtil';

export const router=createBrowserRouter(
    [
        {
            path:'/login?',
            element:<Login />,
        },
        {
            path:'/chat',
            element:<RouteAuth><Chat /></RouteAuth>,
            loader:getUserStatus
        }
    ]
);