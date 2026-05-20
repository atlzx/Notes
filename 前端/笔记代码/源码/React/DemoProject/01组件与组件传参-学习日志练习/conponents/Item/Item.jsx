import Date from './Date/Date'
import Description from './Description/Description'
import './Item.css'


// 我们一般将接收的参数命名为props
const Item=(props)=>{
    // 为了保证JSX代码的整洁性，我们一般使用变量接收处理后的props后再插进JSX中
    let myDate=props.myDate;
    let desc=props.desc;
    let time=props.time;
    return  <div className="item">
                <Date myDate={myDate}/>
                <Description desc={desc} time={time}/> {/* 这些传入的参数会被封装为一个对象传递给生成这些组件的函数 */ }
            </div>
}

export default Item;