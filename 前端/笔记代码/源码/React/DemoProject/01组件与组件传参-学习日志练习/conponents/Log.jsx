
import Item from './Item/Item';
import './Log.css'


// 模拟后端返回的数据
let objArr=[
    {
        id:'001',
        myDate:new Date(2001,1,5,6,30),
        desc:'学习JavaScript',
        time:50
    },
    {
        id:'002',
        myDate:new Date(2005,4,28,10,20),
        desc:'学习TypeScript',
        time:40
    },
    {
        id:'003',
        myDate:new Date(2008,7,14,9,38),
        desc:'学习Python',
        time:20
    }
];

// 处理数据，得到渲染后的Item
const arr=objArr.map(
    (item)=>{
        /* 这些传入的参数会被封装为一个对象传递给生成这些组件的函数 */
        return <Item key={item.id} myDate={item.myDate} desc={item.desc} time={item.time}></Item>
    }
);


// 我们一般将接收的参数命名为props
const Logs=()=>{
    return  <div className="logs">
                {arr}
            </div>;
}

export default Logs;