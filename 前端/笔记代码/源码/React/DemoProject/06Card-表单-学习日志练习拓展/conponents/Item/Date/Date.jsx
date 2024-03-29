import './Date.css'


// 我们一般将接收的参数命名为props
const Date=(props)=>{
    // 为了保证JSX代码的整洁性，我们一般使用变量接收处理后的props后再插进JSX中
    let month=props.myDate.toLocaleDateString("zh-CN",{month:"long"});
    let day=props.myDate.getDate();
    return  <div className="date">
                <div className="month">{month}</div>
                <div className="day">{day}</div>
            </div>;
}


export default Date;