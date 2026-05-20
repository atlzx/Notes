<script setup>
  import {useRouter} from 'vue-router'
  let route=useRouter();
  
  /* 进行事件委派 */
  document.addEventListener(
    "click",
    (event)=>{
      let buttons=document.getElementsByTagName("button");
      console.log(buttons);
      let index=[...buttons].indexOf(event.target);
      if(index===0){
        route.push({path:'/show1/1/Java'});  /* 指定路径，调用path方法自动跳转 */
      }else if(index===1){
        route.push({path:'/show2',query:{id:2,language:'python'}});  /*  query对应存储键值对形式的参数对象 */
      }
    }
  );
</script>

<template>
  <div>
    <router-link to="/show1/1/Java">转到show1,使用关键字传参</router-link><br>
    <button type="button">转到show1,使用关键字传参</button>  <br>
    <router-link to="/show2?id=2&language=python">转到show2，使用路径传参</router-link><br>
    <button type="button">转到show2，使用路径传参</button>
    <router-view name="Show1"></router-view>
    <router-view name="Show2"></router-view>
  </div>
</template>

<style scoped>


</style>
