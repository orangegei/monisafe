<script lang="ts" setup>
import { ref } from 'vue'

const value = ref('')
const defaultTime = ref<[Date, Date]>([
    new Date(2000, 1, 1, 0, 0, 0),
    new Date(2000, 2, 1, 23, 59, 59),
])
</script>

<template>
    <div class="date-picker">
        <div class="block">
            <el-date-picker v-model="value" type="daterange" start-placeholder="Start date" end-placeholder="End date"
                :default-time="defaultTime" style="width: 300px; height: 40px; border-radius: 30px;" />
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