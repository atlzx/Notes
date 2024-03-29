<script setup>
    import {ref} from 'vue'
    let testValue=ref("aaaaa");
    function fn(){
        testValue.value+=1;
    }
    let testValue2=ref("bbbbb");
    function fn2(){
      testValue2.value+=1;
    }
</script>

<template>
  <div>
    <hr>
    <form action="#">
      <!--  使用v-model指令可以进行双向绑定  -->
        <input type="text" v-model="testValue"><button type="button" @click="fn()">点我一下</button>
        testValue={{ testValue }}
        <br>
        <input type="text" v-model="testValue2"> <button type="button" @click="fn2()">点我一下</button>
        testValue2={{ testValue2 }}
    </form>
  </div>
</template>

<style scoped>


</style>
