<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import BarChart from '@/components/BarChart.vue';
import LineChart from '@/components/LineChart.vue';
import PieChart from '@/components/PieChart.vue';
import DoughnutChart from '@/components/DoughnutChart.vue';

// 获取当前时间的前一天
const today = new Date();
const yesterday = new Date(today);
yesterday.setDate(today.getDate() - 1);

// 日期范围（默认展示昨天一天的数据）
const dateRange = ref<[Date, Date]>([
    yesterday,
    yesterday,
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
const barData = ref([]);
const categories = ref([]);

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
                    <div class="bar-chart">
                        <div class="bar-chart-wrapper">
                            <BarChart :chartData="barData" :xAxisData="categories" title="Custom Bar Chart Title" />
                        </div>
                        <div class="bar-chart-text">
                            <span>交易笔数最多的区间是</span><br>
                            <span class="bar-chart-number">￥5,000~10,000</span><br>
                            <span class="bar-chart-number">1,465 </span>
                            <span class="bar-chart-english">deals</span>
                        </div>
                    </div>

                    <div class="line-chart">
                        <div class="line-chart-area">
                            <div class="line-chart-text">
                                <span>总交易金额最多的时间是</span><br>
                                <span style="font-size: 48px;">周六</span><br>
                                <span class="line-chart-number">17,165￥</span>
                            </div>
                            <div class="line-chart-wrapper">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" />
                            </div>
                        </div>

                        <div class="line-chart-area">
                            <div class="line-chart-wrapper">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" />
                            </div>
                            <div class="line-chart-text">
                                <span>总交易笔数最多的时间是</span><br>
                                <span style="font-size: 48px;">周三</span><br>
                                <span class="line-chart-number">2,436 deals</span>
                            </div>
                        </div>
                    </div>

                    <div class="pie-doughnut-chart">
                        <div class="pie-chart">
                            <PieChart :chartData="pieData"></PieChart>
                        </div>
                        <div class="doughnut-chart">
                            <DoughnutChart :chartData="doughnutChartData" title="Chart"></DoughnutChart>
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
    justify-content: flex-start;
}

.picker {
    width: 100%;
    height: auto;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 20px;
    gap: 30px;
}

.display {
    width: 100%;
    flex-grow: 1;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.bar-chart {
    height: 100%;
    width: 28%;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.bar-chart-wrapper {
    width: 100%;
    height: 64%;
    box-sizing: border-box;
    border-radius: 15px;
    background-color: rgba(211, 207, 207, 0.642);
    padding: 15px;
}

.bar-chart-text {
    width: 100%;
    height: 34%;
    box-sizing: border-box;
    border-radius: 15px;
    background-color: rgba(0, 0, 0, 0.893);
    line-height: 2;
    padding-left: 20px;
    color: white;
}

.bar-chart-number,
.line-chart-number {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
    font-size: 30px;
    color: rgb(255, 255, 255);
}

.bar-chart-english {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
}

.line-chart {
    height: 100%;
    width: 38%;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.line-chart-area {
    width: 100%;
    height: 49%;
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    background-color: rgb(255, 255, 255);
    border-radius: 15px;
}

.line-chart-text {
    width: 40%;
    height: 100%;
    box-sizing: border-box;
    line-height: 2;
    padding: 10px;
}

.line-chart-wrapper {
    width: 60%;
    height: 100%;
    box-sizing: border-box;
}

.pie-doughnut-chart {
    height: 100%;
    width: 28%;
    box-sizing: border-box;
    border-radius: 16px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    gap: 15px;
}

.pie-chart {
    width: 100%;
    height: auto;
    box-sizing: border-box;
    background-color: #DDE8F2;
    border-radius: 15px;
    padding: 10px;
}

.doughnut-chart {
    width: 100%;
    flex-grow: 1;
    box-sizing: border-box;
}
</style>
