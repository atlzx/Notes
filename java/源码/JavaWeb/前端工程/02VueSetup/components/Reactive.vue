<script setup>

    import {reactive} from 'vue'  
    let obj=reactive(
        {
            name:'lzx',
            age:18,
            description:'呃呃'
        }
    );  /* reactive方法用于专门用于针对对象(数组、Object等)进行响应式处理 */
</script>

<template>
    <div>
        <hr>
        <ul>
            <!-- 使用v-for指令来渲染列表，并设置列表内容为对应的对象内属性值 -->
            <li v-for="i in obj" v-bind:key="obj" >{{ i }}</li>
        </ul>
    </div>
</template>

<style scoped>

</style>