<script setup>
  /* 在script标签内写入setup，可以不用写setup方法并返回指定集合就能使当前在标签内写的对象都实现想实现的功能 */
  import {ref} from "vue"  // 导入vue中的ref方法
  // 在 vue2中，定义的变量默认就会进行响应式更新，变量一旦发生变化，vue就会将它变化后的值更新到DOM树上，其关联的html元素中的内容也会立即发生变化
  // 在vue3中，定义的变量默认不会进行响应式更新了，此时需要使用ref或reactive方法进行赋值才行
  let count=ref(1);  // 使用ref方法使该变量的值进行响应时更新,使用ref赋值的变量需要修改value属性才能实现数值的变化


  function countAdd(){
    count.value++;  // 使用ref赋值的变量需要修改value属性才能实现数值的变化
  }

  function countDecrease(){
    if(count.value===0){
      alert('不能再减了');
    }else{
      count.value--;
    }
    
  }
</script>

<template>
  <div>
    <hr>
    <!--  使用@click在按钮被点击时触发countAdd方法  -->
    <button type="button" @click="countAdd()">+</button>
    <!--  使用v-text指令进行count变量与span标签之间的关联  -->
    <span v-text="count"></span>
    <button type="button" @click="countDecrease()">-</button>
  </div>
</template>

<style scoped>
</style>
