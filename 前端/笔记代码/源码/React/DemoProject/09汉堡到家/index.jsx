import reactDOM from 'react-dom/client'
import App from './App'
import './index.css';
import React from 'react'

/* 适配移动端的视口，以便在设置大小时使用rem进行设置 */
document.documentElement.style.fontSize=100/750+'vw';

const root=reactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <App />
     </React.StrictMode>
);