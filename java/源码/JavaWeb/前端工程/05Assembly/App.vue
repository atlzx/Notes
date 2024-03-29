<script setup>
import top from './components/top.vue';
import left from './components/left.vue';
import right from './components/right.vue';
import {ref} from 'vue'
let thisData=ref("");

/* 定义一个对应的receive函数来接收参数 */
function receive(data){
  thisData.value=data;
}

</script>

<template>
  <div class="container">
    <top></top>
    <!-- 在子组件的标签上写上对应的 @sendMessage ，后面的值是用来接收子组件传递的参数的函数，之所以写sendMessage是因为子组件在提交时写的sendMessage -->
    <left @sendMessage="receive"></left>
    <!-- 如果父元素想将参数传递给其它子组件，需要在想传递的子组件标签内写入 :xxx="xxx" getData是子组件用来接收的参数名称，thisData是想传递给子组件的参数-->
    <right :getData="thisData"></right>
  </div>
</template>

<style scoped>
  .container{
    width: 100%;
  }
  .container::after{
    content: '';
    display: block;
    clear: both;
  }
</style>
