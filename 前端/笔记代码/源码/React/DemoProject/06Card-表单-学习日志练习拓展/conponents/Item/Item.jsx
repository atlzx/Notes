import Date from './Date/Date'
import Description from './Description/Description'
import './Item.css'
import Card from '../UI/Card/Card'
import { useState } from 'react'
import ConfirmModel from '../UI/ConfirmModel/ConfirmModel'


// 我们一般将接收的参数命名为props
const Item=(props)=>{
    // 为了保证JSX代码的整洁性，我们一般使用变量接收处理后的props后再插进JSX中
    let myDate=props.myDate;
    let desc=props.desc;
    let time=props.time;
    const [tipState,setTip]=useState(false);

    // 展示提示框
    const showTip=()=>{
        setTip(true);
        console.log('函数执行了');
    }
    // 隐藏提示框
    const hideTip=()=>{
        setTip(false);
    }
    // 确认删除操作并删除
    const confirmDelete=()=>{
        props.deleteLog();
        hideTip();
        console.log('函数执行了');
    }


    /* // 进行删除操作的函数
    const deleteOperate=()=>{

        // 提示删除后无法恢复
        props.showTip(props.deleteLog);
        if(window.confirm('确定要删除该日志吗，删除后无法恢复')){
            // 调用传来的删除方法，该方法连续传递了两层组件，但本质上是在调用App组件内的deleteLog函数
            // 此处无需再传递index参数，因为在上一层已经使用闭包传递了参数进去
            props.deleteLog();
        }
    } */

    return  <Card className="item">
                {
                /* 
                    这个backdrop不应该作为子元素被渲染，因为他可能会受到其父元素、以及其它兄弟元素的定位、浮动等现象的干扰
                    我们需要将它放在一个尽可能层级高的容器内，以防止上述问题的出现
                    这样的话，可以使用Portal来指定元素将被渲染在哪里
                */
                }
                {tipState&&<ConfirmModel hideTip={hideTip} confirmDelete={confirmDelete} textTip={"确定要删除吗?该操作无法取消"}></ConfirmModel>}
                <Date myDate={myDate}/>
                <Description desc={desc} time={time}/> {/* 这些传入的参数会被封装为一个对象传递给生成这些组件的函数 */ }
                <div className='delete-icon' onClick={showTip}>✖</div>  {/* 生成一个小标签，点击时执行删除操作 */}
            </Card>
}

export default Item;