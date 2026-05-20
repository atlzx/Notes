
import classes from './BackDrop.module.css'
import ReactDOM from 'react-dom';

const BackDrop=(props)=>{
    const backDropRoot=document.getElementById("backdrop-root");  // 得到想渲染到的指定位置的DOM对象
    const change=()=>{
        props.notShow();  // 调用传递过来的函数进行backDrop的展示
    };




    // 调用createPortal方法将组件渲染到DOM的指定位置
    return ReactDOM.createPortal(
        (
            <div className={`${classes.backDrop} ${props.className}`} onClick={change}>
                {props.children}
            </div>
        ),
        backDropRoot
    );
};

export default BackDrop;
