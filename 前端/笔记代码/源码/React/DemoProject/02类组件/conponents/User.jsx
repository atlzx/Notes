

import React, { Component } from 'react'


// 类组件必须继承Component类并实现render方法
class User extends Component {


    // 类组件中，通过React.createRef()来得到对应的ref
    divRef=React.createRef();
    

    // 类的State存储在类的实例对象中，在类中，可以直接通过state修改或访问，但在方法中应该使用this来访问
    // 因为所有的state属性都会存储在该state中，因此state需要为一个对象
    state={
        name:'张三',
        age:18,
        desc:'这是个男的',
        count:0
    };
    


    // 为了省事，在类中定义的方法都应该使用箭头函数定义
    add=()=>{
        console.log(this.divRef.current);
        this.setState(
            // 在类组件中，react只会修改我们返回对象中含有的属性，并不会直接覆盖原来的state
            (preObj)=>{
                return {name:'李四',count:preObj.count+1};
            }
        );
    };


    decrease=()=>{
        this.setState(
            (preObj)=>{
                return {age:20,count:preObj.count-1};
            }
        );
    }

    // 除这个render方法外，为省事，其它函数或方法都应该使用箭头函数来定义，因为箭头函数的this是固定的
    render() {
        return (
        <div>
            {
            /* 
                在render方法中，应该使用this来访问类中的属性 
                在对应标签内写入 ref={xxx} 来让该标签成为ref中的属性
                props属性存储于类的实例对象中，使用this.props以访问
            */
            }
            <div ref={this.divRef}>{this.props.desc}</div>
            <h1>{this.state.count}</h1>
            <div>
                <button onClick={this.decrease}>-</button>
                <button onClick={this.add}>+</button>
                <ul>
                    <li>{this.state.name}</li>
                    <li>{this.state.age}</li>
                    <li>{this.state.desc}</li>
                </ul>
            </div>
        </div>
        );
    }
}



export default User;