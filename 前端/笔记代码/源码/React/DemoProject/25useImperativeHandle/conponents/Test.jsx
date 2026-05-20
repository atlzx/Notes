import { useImperativeHandle, useRef } from "react";
import React from 'react'

const Test=React.forwardRef(
    // 被forwardRef包裹的函数与普通的函数式组件相比，可以接收两个参数了，第一个参数是props,第二个参数为ref
    // 第二个参数ref可以向其父组件传递想要交给它的具体的与HTML元素绑定的ref，想给哪个就在哪个元素上写ref={ref}即可
    // 但该方式存在隐患，因为这会导致各组件的耦合性变高，使其它组件可以随意修改自己组件内的元素，并使后期维护变得困难
    // 因此，一般使用React提供的②useImperativeHandle钩子函数与forwardRef配合使用
    function Test(props,ref){

        const inputRef=useRef();

        const clickHandler=()=>{
            console.log(inputRef.current.value);
        };

        // useImperativeHandle钩子函数有两个参数，第一个参数即forwardRef内的函数所接收的第二个参数ref
        // 第二个参数是一个回调函数，回调函数的返回值会成为第一个参数ref.current的值
        // 通过这种方式，我们便可以自行指定想传给ref的值了，而不是暴露性的将能够修改整个元素的DOM对象传过去
        // 这意味着本组件拥有主动权，从而保证本组件内部的元素不会被其它组件肆意修改
        useImperativeHandle(
            ref,
            ()=>{
                return {
                    setValue:(value)=>{
                        inputRef.current.value=value;
                    }
                };
            }
        );

        return (
            <div>
                <input type="text" ref={inputRef}/>
                {/* <button onClick={clickHandler} ref={ref}>获取输入框的值</button> */}
                <button onClick={clickHandler}>获取输入框的值</button>
            </div>
        );
    }
)

export default Test;