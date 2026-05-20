
import {create} from 'zustand';

/*
    定义store非常简单，只需要导入其create函数即可，create函数的返回值是一个函数，一般都以钩子函数的命名规范为接收其返回值的变量命名(即以use作为前缀)
    create函数接收一个回调函数作为参数，该回调函数接收set函数用以更新store中的state的值
    set函数返回的对象将会成为store中的值，这些值都可以被组件直接拿来用
    set函数在使用时与useState返回的setState使用方法是一致的，既可以直接赋值也可以接收一个回调函数
    不同的是它还有第两个参数，用来控制赋值模式，详情见下面的注释
        设置为false时表示将设置的值与原值合并，默认是false
        设置为true时表示用设置的值覆盖原值
*/
export const useZustandDemoStore = create(
    set=>{
        return {
            test:'haha',
            status:false,
            setTest:(newVal)=>{
                // 不设置第二个参数时，会使用合并模式，即test会被赋新值，而其它未被赋值的state也不会被移除而是保持原值
                set({test:newVal});
            },
            setStatus:(status)=>{
                set(
                    state=>{
                        if(state.status===false){
                            return {status};
                        }
                        return {};
                    }
                )
            },
            removeStatus:()=>{
                // 第二个参数设置为true时，该store的五个state将被其新设置的state覆盖，该set函数仅设置了test这一个state，因此其它state将被移除
                set({test:'haha'},true);
            }
        }
    }
);