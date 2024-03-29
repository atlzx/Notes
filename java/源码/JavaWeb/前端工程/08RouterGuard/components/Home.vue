<script setup>
  import {useRouter} from 'vue-router'
  import {ref} from 'vue'
  let router=useRouter();
  let userName=ref('');
  let pwd=ref('');

  /* 判断登录名和密码是否一致 */
  function judge(){
    if(userName.value==='zhangsan'&&pwd.value==='123456'){
      window.sessionStorage.setItem('userName',userName.value);
      router.push({path:'/view1'});  /* 如果合法，进行跳转 */
    }else{
      alert('账户名或密码不正确');
    }
  }
</script>

<template>
    <form action="view1">
      账户:<input type="text" v-model="userName"><br>
      密码:<input type="password" v-model="pwd"><br>
      <button type="button" @click="judge()">登录</button>
    </form>
</template>

<style scoped>
</style>
