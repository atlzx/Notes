
import {useRef} from 'react'


const App=()=>{

    // 当我们需要一个对象或值不想因为组件的重新渲染而改变时，可以使用useRef钩子函数来得到该值
    // useRef仅在参数第一次被传递时起效果，后面在再执行时不会再生效，因此它在组件渲染前后负责传递数据
    // useRef生成的对象，每次渲染后都会得到相同的对象,而不是每次渲染后都会再创建一个里面的属性值与渲染前的属性值相同的对象
    // 由于useRef生成的对象是一个原生JS对象，因此我们也能手动创建一个对象，但这么做没有useRef好，因为useRef得到的对象每次都是相同的，不会消耗多余的内存空间，而我们手动创建的每次渲染都会重新创建一份
    const ref=useRef(null);
    const inputRef=useRef();
    console.log('刚刚生成的对象:',ref);// useRef函数返回的对象是仅有一个current属性的Object对象，我们在得到这个对象后，需要通过current索引才能得到对应的数据


    const handler=()=>{
        ref.current="啊啊啊";
        console.log('修改后的对象:',ref);
    };

    const readHandler=()=>{
        console.log(inputRef.current.value);
    };
    
    return (
        <div>
            <div>{ref.current}</div>
            <input type="text" ref={inputRef} />{/* 使用ref属性进行绑定，使ref的current属性存储与之关联的HTML元素的属性 */}
            <div>
                {/* useRef得到的对象无法被react监听变化而渲染，因为它仅为一个原生js对象 */}
                <button onClick={handler}>转换</button>
                <button onClick={readHandler}>点我读取输入框信息</button>
            </div>
        </div>
    );
}

export default App;