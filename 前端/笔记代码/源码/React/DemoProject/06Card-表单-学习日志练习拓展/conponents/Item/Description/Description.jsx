import './Description.css'


const Description=(props)=>{
    let desc=props.desc;
    let time=props.time;


    
    return  <div className="description">
                <div className="desc">{desc}</div>
                <div className="time">{time}分钟</div>
            </div>;
}


export default Description;