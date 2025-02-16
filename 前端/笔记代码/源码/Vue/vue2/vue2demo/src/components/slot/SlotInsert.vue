<script>
    import OutSlot from './OutSlot.vue';
    import InnerSlot from './InnerSlot.vue';
    export default{
        name:'SlotInsert',
        components:{
            OutSlot,
            InnerSlot
        }
    }
</script>

<template>
    <div>
        插槽示例:
        <out-slot>
            <!-- 
                此处的slot-scope用来接收out-slot传过来的插槽参数，它读取数据的方式类似于React函数式组件的props参数
                即slotProps.[out-slot组件内定义的参数名].xxx
                另外，slot-scope/v-slot指令仅能在template标签内使用
            -->
            <template slot-scope="slotProps">
                显示状态:{{ `${slotProps.obj.show}` }}
                <inner-slot></inner-slot>
            </template>
        </out-slot>
    </div>
</template>

<style>

</style>