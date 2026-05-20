<script setup>

    /*  
        defineProps用来接收父元素传递的参数,其中参数被一个对象接收
        对象内写参数的名称与类型
        getData要与父组件在子组件标签上写的名称一致
        String表示传递的参数的类型
        接收完后，可以直接使用
    */
    defineProps(
        {
            getData:String
        }
    );

</script>

<template>
    <div class="right">
        <!--  利用插入表达式直接使用getData  -->
        {{ getData }}
    </div>
</template>

<style scoped>
.right{
    float: left;
    height: 400px;
    box-sizing: border-box;
    border: 1px solid yellow;
    width: 85%;
}
</style>
