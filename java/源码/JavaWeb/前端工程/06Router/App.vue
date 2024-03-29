<script setup>
  import {ref} from 'vue'
  import {useRouter} from 'vue-router'  /* 导入useRouter来创建一个可以跳转页面的路由对象 */

  let text=ref("");

  let router=useRouter();  // 创建一个可以跳转页面的路由对象


  function show(){
    // 调用push方法直接转到对应路径上
    // router.push(text.value);
    // 也可以传递一个对象来转到对应路径，对象中应包含一个path属性
    // 使用这种方式而不用router-link进行跳转的方式被称为编程式路由
    router.push({path:text.value});
  }


</script>

<template>
  <div>
    <!-- 
      router-link标签专门用于跳转router-view
      在to属性中写入路径即可使对应router-view呈现其对应的组件
      标签体内的内容以文本形式被呈现出来
      该标签也可以写在组件内部
    -->
    <router-link to="/home">到home</router-link><br>
    <router-link to="/list">到list</router-link><br>
    <router-link to="/add">到add</router-link><br>
    <router-link to="/update">到update</router-link><br>
    <hr>
    <!-- 
      router-view标签用来呈现可以进行替换的路由组件
      如果不写name属性，那么一个路由组件在所有的标签上都会显示，且此时router.js中需要使用component而非components
      如果写了name属性，那么路由组件只会在其对应name属性值的标签上显示，此时router.js需要使用components
      一般来说，一个vue文件有一个router-view就够了
     -->
    <router-view name="homeView"></router-view><br>
    <router-view name="listView"></router-view><br>
    <router-view name="addView"></router-view><br>
    <router-view name="updateView"></router-view><br>
    <button type="button" @click="show()">跳转</button><input type="text" v-model="text" > {{ text }}
    <hr>
    <h1>欢迎</h1>
  </div>
</template>

<style scoped>

</style>
