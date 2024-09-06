<script lang="ts" setup>
import { ref } from 'vue'
import FrameView from '../FrameView.vue';
import ChinaMap from '@/components/ChinaMap.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import DataTable from '@/components/DataTable.vue';

const today = new Date();
const yesterday = new Date(today);
yesterday.setDate(today.getDate() - 1);

// 日期范围（默认展示昨天一天的数据）
const dateRange = ref<[Date, Date]>([
    yesterday,
    today,
]);

function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

// 定义地图数据
const mapData = ref([
    { name: '北京市', value: Math.round(Math.random() * 1000) },
    { name: '天津市', value: Math.round(Math.random() * 1000) },
    { name: '上海市', value: Math.round(Math.random() * 1000) },
    { name: '重庆市', value: Math.round(Math.random() * 1000) },
]);

// 定义 Record 接口
interface Record {
    city: string
    money: number
    amount: number
}

// 定义表格数据
const tableData = ref<Record[]>([
    {
        city: '北京', money: 1000, amount: 100
    },
    {
        city: '杭州', money: 2000, amount: 200
    },
    {
        city: '广州', money: 3000, amount: 300
    },
    {
        city: '深圳', money: 4000, amount: 400
    },
    {
        city: '上海', money: 5000, amount: 500
    },
    {
        city: '北京', money: 1000, amount: 100
    },
    {
        city: '杭州', money: 2000, amount: 200
    },
    {
        city: '广州', money: 3000, amount: 300
    },
    {
        city: '深圳', money: 4000, amount: 400
    },
    {
        city: '上海', money: 5000, amount: 500
    },
    {
        city: '北京', money: 1000, amount: 100
    },
    {
        city: '杭州', money: 2000, amount: 200
    },
    {
        city: '广州', money: 3000, amount: 300
    },
    {
        city: '深圳', money: 4000, amount: 400
    },
    {
        city: '上海', money: 5000, amount: 500
    },
    {
        city: '北京', money: 1000, amount: 100
    },
    {
        city: '杭州', money: 2000, amount: 200
    },
    {
        city: '广州', money: 3000, amount: 300
    },
    {
        city: '深圳', money: 4000, amount: 400
    },
    {
        city: '上海', money: 5000, amount: 500
    },
    {
        city: '北京', money: 1000, amount: 100
    },
    {
        city: '杭州', money: 2000, amount: 200
    },
    {
        city: '广州', money: 3000, amount: 300
    },
    {
        city: '深圳', money: 4000, amount: 400
    },
    {
        city: '上海', money: 5000, amount: 500
    },
]);
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="china-map">
                    <ChinaMap :data="mapData" />
                </div>

                <div class="display">
                    <div class="picker">
                        <div class="date-picker-wrapper">
                            <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                        </div>
                        <div class="type-selector">
                            <SelectIcon></SelectIcon>
                        </div>
                    </div>
                    <div class="table-container">
                        <DataTable :tableData="tableData" />
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: space-between;
    height: 100%;
}

.china-map {
    height: 100%;
    width: 60%;
}

.display {
    width: 40%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 2%;
}

.picker {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 20%;
}

.display .picker {
    width: 100%;
    height: auto;
}

.display .table-container {
    width: 100%;
    height: 100%;
    overflow: auto;
}
</style>
