
import MainMenu from "./MainMenu";



// 使用Layout组件来达到将固定组件(MainMenu)和可变组件(props.children)整合到一起的目的，同时Layout组件也是App内最大的组件，它是整个网页中最大的容器
const Layout=(props)=>{
    return (
        <div>
            <MainMenu></MainMenu>
            <hr />
            {/* 从App组件传过来的children在此处被返回 */}
            {props.children}
        </div>
    );
};

export default Layout;