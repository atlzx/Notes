import './Card.css'


// Card组件是一个容器，它专门设置了圆角和阴影，并适配了对其它css类选择器的设置，同时内部可以插入其它JSX代码
// 这样我们只需要在要使用圆角和阴影的时候，用Card标签进行嵌套就可以了

const Card=(props)=>{
    // props.children可以得到标签体Card标签体中的内容
    return <div className={`card ${props.className}`}>{props.children}</div>;
}
    


export default Card;