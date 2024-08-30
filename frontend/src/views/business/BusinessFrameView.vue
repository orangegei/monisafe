<script lang="ts" setup>
import FrameView from '@/views/FrameView.vue'
import { ref } from 'vue'

const value1 = ref('')

const shortcuts = [
    {
        text: 'Last week',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: 'Last month',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: 'Last 3 months',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

defineProps<{
    atmClass?: string;
    forexClass?: string;
}>();
</script>

<template>
    <FrameView>
        <template #atm-forex>
            <div class="atm-forex-container">
                <RouterLink to="/business/atm" style="text-decoration: none; color: inherit;">
                    <div :class="atmClass">ATM</div>
                </RouterLink>
                <RouterLink to="/business/forex" style="text-decoration: none; color: inherit;">
                    <div :class="forexClass">外汇</div>
                </RouterLink>
            </div>
        </template>

        <template #main-body>
            <div class="date-picker">
                <div class="block">
                    <el-date-picker v-model="value1" type="daterange" range-separator="To"
                        start-placeholder="Start date" end-placeholder="End date" />
                </div>
            </div>
            <slot name="main-body-display"></slot>
        </template>
    </FrameView>
</template>

<style scoped>
.atm-forex-container {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 250px;
    border: 1px solid black;
    border-radius: 30px;
}

.atm-forex-container>* {
    flex-grow: 1;
}

/* 根据选择的界面进行展示 */
.atm,
.forex {
    text-align: center;
    font-size: 28px;
    padding: 5px;
    cursor: pointer;
    border-radius: 30px;
    padding: 5px;
}

.atm-active,
.forex-active {
    text-align: center;
    font-size: 28px;
    padding: 5px;
    cursor: pointer;
    border-radius: 30px;
    padding: 5px;
}

.atm {
    color: rgb(0, 0, 0);
}

.atm-active {
    background-color: #0171B9;
    color: white;
}

.forex {
    color: rgb(0, 0, 0);
}

.forex-active {
    background-color: #0171B9;
    color: white;
}

.date-picker {
    display: flex;
    width: 100%;
    padding: 0;
}

.date-picker .block {
    padding: 15px 0;
    text-align: left;
    border-right: solid 1px var(--el-border-color);
    height: auto;
}

.date-picker .block:last-child {
    border-right: none;
}

.date-picker .el-date-picker {
    font-size: 16px;
    color: #333;
}
</style>
