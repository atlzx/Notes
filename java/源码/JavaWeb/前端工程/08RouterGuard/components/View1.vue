<script setup>
  import {useRouter} from 'vue-router'
  let router=useRouter();
  function exit(){
    /* 退出时删除 sessionStorage 中的数据 */
    window.sessionStorage.removeItem('userName');
    router.push({path:'/'});
  }
</script>

<template>
  <div>
    这是第一个视图<br>
    <button @click="exit()">退出登录</button>
  </div>
</template>

<style scoped>
</style>
