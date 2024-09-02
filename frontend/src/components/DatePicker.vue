<script lang="ts" setup>
import { ref, watch, defineProps, defineEmits } from 'vue'

// 定义组件接收的 props
const props = defineProps<{
    time: [Date, Date];
}>()

// 定义 emit 用于触发事件
const emit = defineEmits(['update:internalValue'])

// 内部的 reactive 数据，用于管理 v-model 双向绑定
const internalValue = ref<[Date, Date]>(props.time)

// 将 internalValue 变化时更新到外部
watch(internalValue, (newValue) => {
    emit('update:internalValue', newValue)
})
</script>

<template>
    <div class="date-picker">
        <div class="block">
            <el-date-picker v-model="internalValue" type="daterange" start-placeholder="Start date"
                end-placeholder="End date" :default-time="time"
                style="width: 300px; height: 40px; border-radius: 30px;" />
        </div>
    </div>
</template>

<style scoped>
.date-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.date-picker .block {
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    flex: 1;
}

.date-picker .block:last-child {
    border-right: none;
}
</style>
