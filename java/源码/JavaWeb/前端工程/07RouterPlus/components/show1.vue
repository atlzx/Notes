<script setup>
    import {useRoute} from 'vue-router'  // 导入useRoute函数
    import {ref,onUpdated} from 'vue'

    let route=useRoute();  // 调用useRoute函数，该函数返回一个包含路由参数的对象，该对象可以用来接收传递的参数
    let id=ref(route.params.id);
    let language=ref(route.params.language);
    console.log(route.params.id);


    /* 调用onUpdated函数，在更新完成后给id和language赋值 */
    onUpdated(
      ()=>{
        console.log('函数执行');
        // 使用路径传参时需要使用params
        id.value=route.params.id;
        language.value=route.params.language;
      }
    );
</script>

<template>
  <div>
    <h1>这是第一个展示</h1>
    <span>id号为{{ id }}的{{ language }}语言是世界上最好的语言</span>
  </div>
</template>

<style scoped>
</style>