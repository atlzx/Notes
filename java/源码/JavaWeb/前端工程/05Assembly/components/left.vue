<script setup>

    /* 
        想要子组件向父组件传参，需要导入defineEmits函数
        defineEmits函数接收一个数组，这个数组包含一些传给父组件的方法，父组件会用对应的 @名称 来接收
     */
    import {ref,defineEmits} from 'vue'

    let result=defineEmits(['sendMessage']);  /*  函数的返回值是一个函数  */


    /* 调用这个函数向父组件传参 */
    function send(data){
        result('sendMessage',data);  /*  该函数包括两个参数，第一个参数是在传给defineEmits方法的数组中的值之一，第二个参数是想向父组件传递的参数  */
    }
</script>

<template>
    <div class="left">
        <ul>
            <li @click="send('学员管理')">学员管理</li>
            <li @click="send('图书管理')">图书管理</li>
            <li @click="send('请假管理')">请假管理</li>
            <li @click="send('考试管理')">考试管理</li>
            <li @click="send('讲师管理')">讲师管理</li>
        </ul>
    </div>
</template>

<style scoped>
.left{
    float: left;
    width: 15%;
    box-sizing: border-box;
    border: 1px solid blue;
    height: 400px;
}
ul{
    box-sizing: border-box;
    padding-top: 25px;
}
ul li{
    cursor: pointer;
}
</style>
