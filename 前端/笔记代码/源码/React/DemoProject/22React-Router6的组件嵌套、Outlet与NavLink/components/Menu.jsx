
import { NavLink } from "react-router-dom";

const Menu=()=>{

    const change=({isActive})=>{
        // 下面表示当该超链接被选中时，他会出现偏灰色的背景色
        return isActive?{backgroundColor:'#ccc'}:null;
    };

    return (
        <ul>
            {/* 
                NavLink组件的style属性接收一个回调函数作为参数 ，函数的返回值对象将能够使该组件在不同状态时启用不同的CSS样式
                该回调函数会得到一个对象，对象内有三个属性:isActive、isPending、isTransitioning
                一般常用的是isActive
            */}
            <li><NavLink to={'/'} style={change}>主页</NavLink></li>
            <li><NavLink to={'/B'} style={change}>查看B组件</NavLink></li>
            <li><NavLink to={'/C'} style={change}>查看C组件</NavLink></li>
            <li><NavLink to={'/D/1'} style={change}>查看D组件</NavLink></li>
            <li><NavLink to={'/D/2/E'} style={change}>查看E组件</NavLink></li>
        </ul>
    );
};

export default Menu;