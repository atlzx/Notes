import Logs from './conponents/Log'
import FormItem from './conponents/FormItem/FormItem';
import './app.css'
import { useState } from 'react';



const App=()=>{

    // 由于Item组件用于渲染日志生成，而FormItem组件用于接收用户输入的新的日志，但双方却是兄弟组件，导致出现了组件参的问题
    // 由于logArr此时被两个组件所需要，因此我们可以在它们共同的，且离他们最近的祖先元素定义该数组
    // FormItem向上传参，通过App组建的处理得到最新的数组，再将数组传递给Item进行渲染
    const [logArr,setLogArr]=useState(
        [
            {
                id:'001',
                myDate:new Date(2020,1,5,6,30),
                desc:'学习JavaScript',
                time:50
            },
            {
                id:'002',
                myDate:new Date(2021,4,28,10,20),
                desc:'学习TypeScript',
                time:40
            },
            {
                id:'003',
                myDate:new Date(2022,7,14,9,38),
                desc:'学习Python',
                time:20
            }
        ]
    );


    // 进行数组的添加更新
    const getNewLog=(newLog)=>{
        console.log(newLog);
        setLogArr(
            (previousArr)=>{
                newLog.id=Date.now();  // 给对象指定唯一的ID
                // 数组进行浅拷贝时，也可以直接在后面新增一些元素，如果写在前面那么新增元素会跑到数组最前面，如果写在后面那么新增元素会跑到最后面
                // 数组属于对象，必须返回一个新的对象才能进行比较，因此需要对原数组进行浅拷贝才行
                return [...previousArr,newLog];
            }
        );
    };


    // 进行删除操作
    // 由于添加了根据年份过滤的功能，原来的根据索引删除的功能会出bug，因此需要改变删除方式，使用id删除
    const deleteLog=(id)=>{
        setLogArr(
            (preArr)=>{
                // 浅拷贝数组对象，以得到内存地址不同的数组
                let copyLogArr=[...preArr];
                // 进行数组过滤，筛选出不等于该id的数组
                copyLogArr=copyLogArr.filter(
                    (item)=>{
                        return item.id!==id;
                    }
                );
                return copyLogArr;

                // 下面的代码是原来根据索引删除的代码
                
                /* let copyLogArr=[...preArr];  // 得到浅拷贝的对象
                copyLogArr.splice(index,1); // 调用splice方法移除元素,splice方法是破坏性方法
                return copyLogArr; */
            }
        );
    }


    return (
        
        <div className='app'>
            {
            /* 
                向FormItem传递一个函数，该函数在子组件被调用时，其参数会被父组件中定义的函数读取并操作
                向Logs传递需要渲染的日志所需的数据组成的数组
            */
            }
            <FormItem getNewLog={getNewLog} ></FormItem>
            {/* 给LogArr组件传递deleteLog函数作为参数 */}
            <Logs logArr={logArr} deleteLog={deleteLog}></Logs>
        </div>
    );
}


export default App;