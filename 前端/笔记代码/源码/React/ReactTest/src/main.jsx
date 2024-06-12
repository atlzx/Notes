import React from 'react';
import ReactDOM from 'react-dom/client';
import {RouterProvider} from 'react-router-dom';
import {router} from './routes/route';
import UserContext from './context/UserContext';


ReactDOM.createRoot(document.getElementById('root')).render(
    // <React.StrictMode>
        <UserContext.Provider 
            value={{}}
        >
            <RouterProvider router={router} />
        </UserContext.Provider>
    // </React.StrictMode> 
)
