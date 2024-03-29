<script setup>
    import {watch,watchEffect,ref,reactive} from 'vue'
    let input1=ref("aaaa");
    let input2=reactive(
        {
            name:'lzx',
            age:12,
            hobby:[
                "吃饭",
                "睡觉",
                "打游戏"
            ]
        }
    );
    /* watch函数有明确的数据源，它更加精确 */
    watch(
        input1,
        (newValue,oldValue)=>{
            console.log(`值${oldValue}变化为了${newValue}`);
        }
    );

    watchEffect(
        ()=>{
            /*  在代码中出现的变量，只要有一个发生变化，所有代码都会执行  */
            console.log(input2);
            console.log(input2.name);
            console.log(input2.age);
            console.log(input2.hobby);
        }
    );

    function fn1(){
        input1.value+=1;
    }

    function fn2(){
        input2.name+=1;
    }

    function fn3(){
        input2.age++;
    }
</script>

<template>
    <div>
        <hr>
        <form action="#">
            <input type="text" v-model="input1"><button type="button" @click="fn1()">点我</button>
            <input type="text" v-model="input2.name"><button type="button" @click="fn2()">点我</button>
            <input type="number" v-model="input2.age"><button type="button" @click="fn3()">点我</button>
        </form>
    </div>
</template>

<style scoped>

</style>