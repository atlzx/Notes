<script setup>
    let name=12;
    let str="这是一个字符串";
    let obj=[
        {
            name:"cola",
            price:6,
            number:2
        },
        {
            name:"pencil",
            price:3,
            number:6
        }
    ];
    function getPrice(){
        let result=0;
        for(let i of obj){
            result+=i.price*i.number;
        }
        return result;
    }
    let bee="蜜 蜂";
</script>

<template>
    <div>
        <hr>
        <h1>数字:{{ name+20 }}</h1>  <!-- 在标签内进行执行插入表达式 -->
        <h1>字符串:{{ str }}</h1>
        <h1>输出数组:{{ obj }}</h1>
        <h1>得到数组的总价格:{{ getPrice() }}</h1>  <!--  插入表达式内部可以调用函数  -->
        不写标签也能进行插值:{{ name,str }}  <!--   插入表达式不需要被标签包裹也可以插值   -->
        逆序字符串{{ bee }}:{{ bee.split(' ').reverse().join('-') }}  <!-- 插入表达式支持进行简单的js代码 -->
    </div>
</template>

<style scoped>

</style>