<script setup>
    import {reactive} from 'vue'

    let obj=reactive(
        {
            flag1:true,
            flag2:true,
            flag3:true
        }
    );

    function randomFn(){
        let result;
        for(let i=1;i<=3;i++){
            result=Math.floor(Math.random()*2);
            if(result===0){
                obj[`flag`+i]=false;
            }else{
                obj[`flag`+i]=true;
            }
        }
    }

</script>

<template>
  <div>
    <hr>
    <ul>
        <li v-if="obj.flag1">这是li1</li>  <!-- 当v-if中的obj.flag1为true时才显示，如果为false那么会将该元素从DOM树中被移除 -->
        <li v-else>li1没了</li>  <!--  如果v-if中的obj.flag1为false才显示该元素  -->
        <li v-if="obj.flag2">这是li2</li>
        <li v-show="obj.flag3">这是li3</li> <!-- 使用v-show指令不会使该元素在DOM树中被移除，而会给其添加一个display并赋值none -->
        <button @click="randomFn()">{{ `随机进行处理` }}</button>
        <div>三个属性的值:{{ obj.flag1 }}{{ obj.flag2 }}{{ obj.flag3 }}</div>
    </ul>
  </div>
</template>

<style scoped>

</style>
