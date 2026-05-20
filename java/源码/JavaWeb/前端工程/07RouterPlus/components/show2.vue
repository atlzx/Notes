<script setup>

  import {useRoute} from 'vue-router'  // 导入useRoute函数
    import {ref,onUpdated} from 'vue'  

    let route=useRoute();  // 调用useRoute函数，该函数返回一个包含路由参数的对象
    let id=ref(route.query.id);
    let language=ref(route.query.language);
    console.log(route.query.id);


    /* 调用onUpdated函数，在更新完成后给id和language赋值 */
    onUpdated(
      ()=>{
        console.log('函数执行');
        // 使用关键字传参时需要使用query
        id.value=route.query.id;
        language.value=route.query.language;
      }
    );
  
</script>

<template>
  <div>
    <h1>这是第二个展示</h1>
    <span>id号为{{ id }}的{{ language }}语言是世界上最好的语言</span>
  </div>
</template>

<style scoped>

</style>