
import {useDispatch, useSelector} from 'react-redux';
import { setName,setAge,setGender } from './store/store';



const App=()=>{
    // useSelector接收一个回调函数，该回调函数会接收全部的state，该state是configureStore函数接受的参数配置内的reducer属性
    // 得到的属性内仅包含state相关的值
    const people=useSelector((state)=>{return state.people;});
    // useDispatch能够直接得到store的派发器
    const dispatch=useDispatch();

    const updateNameHandler=()=>{
        // 使用构造器对象来替换掉带着type类型的对象
        // 在构造器对象内传递的参数将作为实际的reducer函数接收的第二个参数出现
        dispatch(setName('王五'));
    };

    const updateAgeHandler=()=>{
        dispatch(setAge(15));
    };

    const updateGenderHandler=()=>{
        dispatch(setGender('女'));
    }

    return (
        <div>
            <span>{people.people1.name}---{people.people1.age}---{people.people1.gender}</span>
            <span>{people.people2.name}---{people.people2.age}---{people.people2.gender}</span>
            <button onClick={updateNameHandler}>修改名称</button>
            <button onClick={updateAgeHandler}>修改年龄</button>
            <button onClick={updateGenderHandler}>修改性别</button>
        </div>
    );
    
};

export default App;