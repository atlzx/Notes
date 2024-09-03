<script setup>
    import {reactive} from 'vue'
    let arr=[
        {
            id:1,
            name:'张三',
            age:18
        },
        {
            id:2,
            name:'李四',
            age:12
        }
    ];
</script>

<template>
  <div>
    <hr>
    <ul>
        <!-- v-for进行li的渲染，由于arr有两个元素，因此会渲染出两个li标签，第一个li标签对应数组内的第一个对象，第二个标签对应第二个对象,index表示对象在数组内的索引 -->
        <li v-for="(i,index) in arr" :key="i.id">
            第{{ i.id }}个人的名字:{{ i.name }},
            年龄是{{ i.age }},
            {{ i.age>=18?'成年':'未成年' }}
        </li>
    </ul>
  </div>
</template>

<style scoped>

</style>
