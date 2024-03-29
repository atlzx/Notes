

function clickParent(){
    alert('div父元素被点击了');
}

function clickAnchor(event){
    event.preventDefault();
    alert('a标签被点击了');  // 取消标签的默认行为
    event.stopPropagation();  // 阻止事件冒泡
}

function clickButton(){
    alert('按钮被点击了');
}

const App=()=>{
    return (
        // 事件触发使用驼峰命名法进行
        // 对于设置像素的css属性，可以省略px不写
        <div onClick={clickParent} style={{backgroundColor:'#bfa',width:200,height:200}}>
            <button onClick={clickButton}>点我一下</button>
            <a href="#" onClick={clickAnchor}>这是一个超链接</a>
        </div>
    );
}
    




export default App;