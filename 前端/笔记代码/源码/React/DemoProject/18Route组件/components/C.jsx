

const C=(props)=>{

    console.log(props);
    console.log(`id=${props.match.params.id}`);  // 输出id的值

    return (
        <div>这是组件C</div>
    );
};

export default C;