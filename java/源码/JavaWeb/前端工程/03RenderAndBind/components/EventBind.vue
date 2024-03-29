<script setup>
    function fn(){
        alert('按钮被点击了');
    }
</script>

<template>
  <div>
    <hr>
    <!-- @是v-on:的简略写法。.once表示事件仅处理一次 -->
    <button @click.once="fn()">点我</button>
  </div>
</template>

<style scoped>


</style>
