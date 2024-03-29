<script setup>
    import {reactive} from 'vue'
    let attr=reactive(
        {
            url:"/src/assets/img/报文部首结构.png",  /*  使用相对路径会出问题  */
            alt:"这是一个图片",
        }
    );
</script>

<template>
    <div>

        <!-- 使用v-bind:命令使属性被渲染，使其绑定vue中定义的变量，该操作可以直接简写为: -->
        <img v-bind:src="attr.url" :alt="attr.alt">
    </div>
</template>

<style scoped>

</style>