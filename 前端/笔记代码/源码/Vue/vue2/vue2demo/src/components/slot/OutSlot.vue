<script>
    export default{
        name:'OutSlot',
        data:()=>{
            return {
                obj:{
                    show:false
                }
            };
        }
    }
</script>

<template>
    <div>
        <!-- 插槽传参的方式就是通过v-bind指令传递想传递的参数，其中v-bind指定的属性名和父组件想拿到数据所要读取的key名一致 -->
        <slot :obj="obj"></slot>
    </div>
</template>

<style>

</style>