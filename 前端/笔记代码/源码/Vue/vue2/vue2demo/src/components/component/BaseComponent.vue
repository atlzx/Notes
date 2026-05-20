<script>
    import ChildrenComponent from './ChildrenComponent.vue';
    export default {
        name:'BaseComponent',  // 这是组件的名称，可以看作组件的唯一标识
        // 在import组件后，如果想要使用组件，还需要在components里面显式声明（如果没有全局注册到Vue实例中）
        components:{
            ChildrenComponent
        },
        // data用来定义响应式数据，它接收一个函数，函数的返回值中的所有的key将会成为组件内的响应式数据
        // 当使用里面的数据时，template标签可以不写this,而在script标签中则必须写this
        data:()=>{
            return {
                count:1,
                childrenValue:1
            }
        },
        // watch用来监听响应式数据的变化，key就是响应式数据的名字，如下示例，就是监听data中的count的变化
        watch:{
            count: (newValue,oldValue)=>{
                console.log(`watch方法执行，newValue:${newValue},oldValue:${oldValue}`);
            }
        },
        // 在此处定义methods方法，定义的方法可以在template标签和其它一些属性被调用
        // 当调用方法时，template标签可以不写this,而在script标签中则必须写this（除本方法内）
        methods:{
            addHandler(){
                this.count++;
            },
            childrenEventHandler(value){
                // 打印子组件传递过来的值
                console.log(value);
            }
        },
        // created生命周期函数在组件创建完成后被立即同步调用，一般用它来发送请求，加载页面数据
        created(){
            console.log('组件创建时就执行');
        }, 
        // mounted生命周期函数在挂载完毕后执行，可以在此处执行一些附加操作
        mounted(){
            console.log('组件挂载后执行');
        },
    }
</script>

<template>
    <div>
        <!-- 通过写:ref="button"可以获得原生的js dom对象，通过this.$ref.button即可访问该对象 -->
        <button @click="addHandler" ref="button">点我一下</button>
        Hello World {{ count }}
        <!-- 
            直接通过v-bind可以向子组件传值，而通过事件监听可以监听到子组件传递过来的对应事件的值
            向子组件传值时，需要保持key与子组件的props中声明的变量名相等
            监听子组件事件时，也需要保证监听的事件与子组件冒泡的事件名相同
        -->
        <children-component :value="childrenValue" @childrenButtonClick="childrenEventHandler"></children-component>
    </div>
</template>




