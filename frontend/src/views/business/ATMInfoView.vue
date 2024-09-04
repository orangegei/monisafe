<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import instance  from '@/utils/request'
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import BarChart from '@/components/BarChart.vue';
import LineChart from '@/components/LineChart.vue';
import PieChart from '@/components/PieChart.vue';
import DoughnutChart from '@/components/DoughnutChart.vue';
import TimeLine from '@/components/TimeLine.vue';

// 获取当前时间的前一天
const today = new Date();
const yesterday = new Date(today);
yesterday.setDate(today.getDate() - 1);

// 日期范围（默认展示昨天一天的数据）
const dateRange = ref<[Date, Date]>([
    yesterday,
    today,
]);

// 当用户在日期选择器中选择新的日期范围时，该函数将被调用它的作用是更新应用内部状态（dateRange.value）以反映用户的新选择
function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

// 将日期转换为字符串并进行URL编码
function formatDate(date: Date): string {
    return encodeURIComponent(date.toISOString());
}

// 发送日期范围到后端
function sendDateRangeToBackend() {
    
    const params = {
        startDate: formatDate(dateRange.value[0]),
        endDate: formatDate(dateRange.value[1]),
    };

    return axios.get('/business/atm/range', { params });
}

// 柱状图数据
const barData = ref([120, 60, 150, 100]);
const categories = ref(['A', 'B', 'C', 'D']);

// 发送柱状图数据到后端
function sendBarDataToBackend() {
    const params = {
        barData: JSON.stringify(barData.value), // 转换为字符串进行URL编码
        categories: JSON.stringify(categories.value), // 转换为字符串进行URL编码
    };

    return axios.get('/business/atm/range', { params });
}

// 并行发送请求
function sendAllDataToBackend() {
    axios.all([sendDateRangeToBackend(), sendBarDataToBackend()])
        .then(axios.spread((dateRangeResponse, barDataResponse) => {
            console.log('Date Range Response:', dateRangeResponse.data);
            console.log('Bar Data Response:', barDataResponse.data);
        }))
        .catch(error => {
            console.error('Error sending data:', error);
        });
}

onMounted(() => {
    sendAllDataToBackend();
});

// 折线图数据
const lineData = ref([120, 200, 150, 80, 70, 110, 130]);
const daysOfWeek = ref(['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']);

// 饼状图数据
const pieData = ref([
    { value: 1048, name: 'Search Engine' },
    { value: 735, name: 'Direct' },
    { value: 580, name: 'Email' },
    { value: 484, name: 'Union Ads' },
    { value: 300, name: 'Video Ads' },
]);

// 环形图数据
const doughnutChartData = ref([
    { value: 1048, name: 'Search Engine' },
    { value: 735, name: 'Direct' },
    { value: 580, name: 'Email' },
    { value: 484, name: 'Union Ads' },
    { value: 300, name: 'Video Ads' }
]);

//时间线数据
const timelineItems  = ref([
  { text: '事件1', color: '#DDE8F2' },
  { text: '事件2', color: '#ebe5e5' },
  { text: '事件3', color: '#cdeded' },
  { text: '事件4', color: '#e0f4fe' },
  { text: '事件5', color: '#e0f4fe' },
]);

</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="picker">
                    <div class="date-picker-wrapper">
                        <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                    </div>
                    <div class="type-selector">
                        <SelectIcon></SelectIcon>
                    </div>
                </div>

                <div class="display">
                    <div class="time-line">
                        <TimeLine :items="timelineItems"/>
                    </div>

                    <div class="chart-section">
                        <div class="chart-row">
                            <div class="pie-chart">
                                <PieChart :chartData="pieData" title="各年龄占比"></PieChart>
                            </div>
                            <div class="bar-chart">
                                <BarChart :chartData="barData" :xAxisData="categories" title="Custom Bar Chart Title"  />
                            </div>
                            <div class="doughnut-chart">
                                <DoughnutChart :chartData="doughnutChartData" title="各阶段占比" ></DoughnutChart>
                            </div>
                        </div>
                        <div class="chart-row2">
                            <div class="line-chart">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" title="各年龄占比"/>
                            </div>
                            <div class="line-chart">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" title="各阶段占比"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.picker {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 2%;
    gap: 5%;
}

.display {
    width: 100%;
    height: 100%;
    display: flex;
}

.time-line {
    width: 25%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.chart-section {
    width: 75%;
    height: 100%;
    display: flex;
    flex-direction: column;
    gap:7%;
}

.chart-row {
    height: 50%;
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.chart-row2 {
    height: 50%;
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.pie-chart{
    flex: 7;
    height: 100%;
    box-sizing: border-box;
    border-radius: 15px;
    background-color: #DDE8F2;
    padding-top:3%;
}

.bar-chart {
    flex: 6;
    height: 100%;
    box-sizing: border-box;
    border-radius: 15px;
    background-color: #ebe5e5;
    margin-left: 15px;
    margin-right: 15px;
}

.doughnut-chart{
    flex: 7;
    height: 100%;
    box-sizing: border-box;
    border-radius: 15px;
    background-color: #cdeded;
    margin-right: 15px;
    padding-top:3%;
}

.line-chart {
    flex: 1;
    box-sizing: border-box;
    height: 80%;
    border-radius: 15px;
    /* background-color: rgba(211, 207, 207, 0.642); */
}

</style>
