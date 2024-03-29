import Card from '../UI/Card/Card';
import './FormItem.css';
import { useState } from 'react';


const FormItem=(props)=>{

    // 定义对象，以进行双向绑定
    const[obj,setObj]=useState(
            {
                myDate:'',
                desc:'',
                time:''
            }
        );

        // 通过处理onChange事件来实时更新input标签的内容，实现表单内容与变量同步更新，
        // 同时调用set函数进行重新渲染，再使标签的value与变量绑定，实现变量与表单内容同步更新
        // 上述情况下，双方只要有一方发生变化，另一方也会随之发生变化，我们称这种表单是可控类表单，这种绑定方式称为双向绑定
    // 监听日期输入框的变化
    const dateHandler=(event)=>{
        setObj(
            (preObj)=>{
                // 函数式组件内，由于传参是直接覆盖上一个变量，因此我们想修改对象的某一属性只能通过传递当前对象的浅拷贝对象并修改想设置的对应值
                return {...preObj,myDate:event.target.value};
            }
        );
    }

    // 监听描述输入框的变化
    const descHandler=(event)=>{
        setObj(
            (preObj)=>{
                return {...preObj,desc:event.target.value};
            }
        );
    }


    // 监听时间输入框的变化
    const timeHandler=(event)=>{
        setObj(
            (preObj)=>{
                return {...preObj,time:event.target.value};
            }
        );
    }

    // 处理提交操作
    const submitForm=(event)=>{
        event.preventDefault();
        // 给父元素传递对象
        props.getNewLog(
            {
                myDate:new Date(obj.myDate),
                desc:obj.desc,
                time:+obj.time
            }
        );
        // 提交后清空表单
        setObj({
            myDate:'',
            desc:'',
            time:''
        });
    }





    return (
        <Card className="log-form">
            <form action="#" onSubmit={submitForm}>
                <div className='form-item'>
                    <label htmlFor="myDate">日期</label>
                    <input type="date" id='myDate' onChange={dateHandler} value={obj.myDate}/>{/* 通过onChange事件来监听表单标签的变化 */}
                </div>
                <div className='form-item'>
                    <label htmlFor="desc">内容</label>
                    <input type="text" id='desc' onChange={descHandler} value={obj.desc}/>
                </div>
                <div className='form-item'>
                    <label htmlFor="time">时长</label>
                    <input type="number" id='time' onChange={timeHandler} value={obj.time}/>
                </div>
                <div className='form-btn'><button>提交</button></div>
            </form>
        </Card>
    );
};


export default FormItem;