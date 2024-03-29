
import {configureStore, createSlice} from '@reduxjs/toolkit'

// 使用createSlice创建切片对象
const people=createSlice(
    {
        name:'p',
        initialState:{
            people1:{
                name:'张三',
                age:18,
                gender:'男'
            },
            people2:{
                name:'李四',
                age:20,
                gender:'女'
            }
        },
        reducers:{
            setName:(state,action)=>{
                // 在该函数内，我们只需要对我们想修改的值进行修改，而不必再像以前一样先解构再针对想修改的值修改了
                state.people1.name=action.payload;
            },
            setAge:(state,action)=>{
                state.people1.age=action.payload;
            },
            setGender:(state,action)=>{
                state.people1.gender=action.payload;
            }
        }
    }
);

// 向外暴露切片对象的各方法构造器，以方便更改数值
// 得到构造器对象意味着我们不必每次调用dispatcher的时候都需要传递一个带着type属性的对象，而仅需要向dispatch内传递构造器对象即可
// 每个action对象的结构都是{type/name函数名,payload:函数的参数}
export const {setName,setAge,setGender}=people.actions;

// 调用configureStore创建store，并向外暴露
export const store=configureStore(
    {
        reducer:{
            people:people.reducer
        },
    }
);
