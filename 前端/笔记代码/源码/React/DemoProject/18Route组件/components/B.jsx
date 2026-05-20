const B=(props)=>{

    const replaceHandler=()=>{
        /* 
            使用replace方法后，会跳转到指定页面，同时本历史记录会覆盖之前的历史记录导致上一个历史记录不能被访问，向前回退将会回退到上上个历史记录
        */
        props.history.replace(
            {
                pathname:'/C/123',
                state:{
                    name:'啊啊啊'
                }
            }
        );
    };

    const pushHandler=()=>{

        // 使用push方法会跳转到指定页面，同时将本页面添加到历史记录内
        // pathname用于指定url
        // state用于指定跳转后组件可以接收到的值，该对象存储在location属性内的state中
        props.history.push(
            {
                pathname:'/C/123',
                state:{
                    name:'啊啊啊'
                },
                search:'bbb'  // search就是url后跟着的?aaa=bbb,xxx=yyy之类的东西
            }
        );
    }

    return (
        <>
            <button onClick={replaceHandler}>点我覆盖本历史记录</button>
            <button onClick={pushHandler}>点我添加历史记录</button>
            <div>
                这是组件B
            </div>
        </>
    );
};

export default B;