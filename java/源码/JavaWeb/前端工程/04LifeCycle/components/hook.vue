<script setup>
  import {onBeforeMount,onMounted,onBeforeUpdate,onUpdated,ref,reactive} from 'vue'
  let message=ref(100);

  /* 通过document.getElementById判断其是否已经挂载到了网页的大DOM树中 */
  onBeforeMount(
    ()=>{
      console.log(document.getElementById("span"));
      console.log('在构建前执行');
    }
  );

  onMounted(
    ()=>{
      console.log(document.getElementById("span"));
      console.log('在构建后执行');
    }
  );


  /* 通过查看HTML端的textContent来验证该函数在之前还是之后执行，但是直接检查message的值貌似都输出相同的值 */
  onBeforeUpdate(
    ()=>{
      console.log(`message将要进行改变了`);
      console.log(document.getElementById("test").textContent);
    }
  );

  onUpdated(
    ()=>{
      console.log(`message已经进行改变了`);
      console.log(document.getElementById("test").textContent);
    }
  );
</script>

<template>
  <div>
    <input type="text" v-model="message" id="span">
    <span id="test">{{ message }}</span>
  </div>
  
</template>

<style scoped>

</style>
