import TestContext from './TestContext'

// 不使用钩子函数来得到context
const B=()=>{
    return (
        // 返回时，想得到context的标签必须被context.Consumer标签包裹
        <TestContext.Consumer>
            {/* 标签内部必须要传递一个回调函数，该回调函数接收一个参数，该参数即为想要得到的context */}
            {
                (context)=>{
                    console.log(1);
                    // 在回调函数中以进行jsx代码的编写
                    return (
                        <div>{context}</div>
                    );
                }
            }
        </TestContext.Consumer>
    );
}
    

export default B;