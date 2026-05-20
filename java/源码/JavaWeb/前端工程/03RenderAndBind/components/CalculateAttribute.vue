<script setup>
    /* 导入依赖的方法 */
    import {reactive,computed} from 'vue'
    let obj=reactive(
     {
        name:'lzx',
        book:[
            'Vue 2 - Advanced Guide',
            'Vue 3 - Basic Guide',
            'Vue 4 - The Mystery'
        ]
     }
    );


    /* 每次调用该函数都会执行 */
    function hasBook(){
        console.log('使用普通函数进行判断')
        return obj.book.length>0?'yes':'no';
    }


    /* 调用computed函数，如果此次执行之前，执行过完全相同的操作，那么操作只会执行一次，故只输出一次控制台打印的操作 */
    let result=computed(
        ()=>{
            console.log('使用computed函数进行判断')
            return obj.book.length>0?'yes':'no';
        }
    );
</script>

<template>
    <div>
        <hr>
        <div>{{ hasBook() }}</div>
        <div>{{ hasBook() }}</div>
        <div>{{ hasBook() }}</div>
        <div>{{ result }}</div>
        <div>{{ result }}</div>
        <div>{{ result }}</div>
    </div>
</template>

<style scoped>

</style>