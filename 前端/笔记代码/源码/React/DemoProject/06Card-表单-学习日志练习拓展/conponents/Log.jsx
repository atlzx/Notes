
import Item from './Item/Item';
import './Log.css'
import Card from '../conponents/UI/Card/Card'
import FiltSelect from './FiltSelect/FiltSelect';
import { useState } from 'react';





// 处理数据，得到渲染后的Item



// 我们一般将接收的参数命名为props
const Logs=(props)=>{

    // 得到删除函数
    const deleteLog=props.deleteLog;
    // 在组件中定义year的state属性，用来与select进行双向绑定
    // 由于select与Log两者所在的组件都需要使用该变量，因此肯定是在父组件中定义该变量，更方便一些
    const [year,setYear]=useState(2022);

    // filterArr用来存储过滤后的数组对象
    const filtArr=props.logArr.filter(
        (item)=>{
            // 根据年份判断是否满足条件
            return item.myDate.getFullYear()===year;
        }
    );

    
    // const filterArr=props.logArr.filter(
    //     (item)=>{
    //         return item===year;
    //     }
    // );
    

    // 模拟后端返回的数据
    // let logArr=props.logArr;

    // 根据数组生成JSX代码，添加了根据年份过滤的功能，因此直接用过滤后的数组处理就好了
    let arr=filtArr.map(
        (item)=>{
            /* 
                这些传入的参数会被封装为一个对象传递给生成这些组件的函数 
                继续向组件内传递deleteLog函数，在此处可以选择是给每一个Item都传递对应的id，还是直接闭包传递deleteLog函数，函数内已经内置了调用deleteLog(id)的方法
                闭包实际上是传递了一个箭头函数对象给下一层组件，只要组件调用该函数，那么函数将会执行里面的deleteLog(id)方法，从而连带调用App组件内的deleteLog方法
            */
            return <Item key={item.id} myDate={item.myDate} desc={item.desc} time={item.time} deleteLog={()=>{return deleteLog(item.id)}}></Item>
        }
    );

    // 如果得到的数组长度为0，那么需要生成一个提示信息来提示没有日志了
    if(arr.length===0){
        arr=[<p className='no-log' key={'noLog'}>没有日志了</p>];
    }

    return  <Card className="logs">
                {/* 在向组件传入year和setYear函数，用来进行双向绑定 */}
                <FiltSelect year={year} setYear={setYear}></FiltSelect>
                {/* 使用且运算符来通过tipState变量的布尔值来决定该组件显不显示 */}
                {arr}
            </Card>;
}

export default Logs;