<script setup>
import HelloWorld from './components/HelloWorld.vue'
import test from "./components/test.vue"  // 通过引用vue文件作为对象，将其它vue组件导入到App.vue文件中来
</script>

<template>
  <!-- 根据Vue的规范，template标签内仅能有一个根标签，通常在外面使用一个div标签将我们要添加的组件包起来，使我们的组件成为div标签的子元素 -->
  <div>
    <test></test>  <!-- 直接将导入的vue文件的别名作为标签写入到template标签内就可以实现对Vue组件的导入 -->
    <h1 class="appColor">呃呃呃呃呃</h1>
    <HelloWorld msg="Vite + Vue" />
  </div>
</template>

<style scoped>
  /*  scoped是Vue用于限制样式设置的一种方式，它会将样式限制在组件中，而不会影响全局样式设置  */
</style>
