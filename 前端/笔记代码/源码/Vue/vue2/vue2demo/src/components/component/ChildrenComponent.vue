<script>
    export default {
        name:'ChildrenComponent',  // 这是组件的名称，可以看作组件的唯一标识
        // data用来定义响应式数据，它接收一个函数，函数的返回值中的所有的key将会成为组件内的响应式数据
        // 当使用里面的数据时，template标签可以不写this,而在script标签中则必须写this
        props:{
            value:{
                type:Number,
                default:null
            }
        },
        data:()=>{
            return {
                count:1
            }
        },
        // computed可以计算数据并返回，一般用于处理父组件传递过来的参数
        // computed会在props和data加载完毕后执行
        computed:{
            newProp:{
                get(){
                    return 'a'+this.value+'a';
                },
                set(v){
                    this.newProp=v;
                }
            }
        },
        methods:{
            buttonClickHandler(){
                // 子组件可以通过这种方式向父组件传递一个事件冒泡，并把参数作为第二个参数传给父组件
                this.$emit('childrenButtonClick','子组件按钮被点击了')
            }
        }
    }
</script>

<template>
    <div>
        父组件传递过来的经过computed处理的参数:{{ newProp }}
        <button @click="buttonClickHandler">这是子组件的按钮</button>
    </div>
</template>
