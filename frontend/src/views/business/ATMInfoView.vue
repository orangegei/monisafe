<script lang="ts" setup>
import { ref } from 'vue';
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import BarChart from '@/components/BarChart.vue';
import LineChart from '@/components/LineChart.vue';
import PieChart from '@/components/PieChart.vue';
import DoughnutChart from '@/components/DoughnutChart.vue';

// 日期范围
const dateRange = ref<[Date, Date]>([
    new Date(2024, 8, 1),
    new Date(2024, 8, 2),
]);

function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

// 柱状图数据
const barData = ref([5, 20, 36, 10]);
const categories = ref(['A', 'B', 'C', 'D']);

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
                            <DoughnutChart :chartData="doughnutChartData" title="Doughnut Chart"></DoughnutChart>
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
    width: 30%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.bar-chart-wrapper {
    width: 100%;
    height: 65%;
}

.bar-chart-text {
    width: 100%;
    flex-grow: 1;
    line-height: 2;
    margin-left: 20px;
}

.bar-chart-number,
.line-chart-number {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
    font-size: 30px;
    color: rgb(0, 0, 0);
}

.bar-chart-english {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
}

.line-chart {
    width: 40%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.line-chart-area {
    width: 100%;
    height: 50%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.line-chart-text {
    width: 40%;
    height: 100%;
    line-height: 2;
}

.line-chart-wrapper {
    width: 60%;
    height: 100%;
}

.pie-doughnut-chart {
    width: 30%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.pie-chart {
    width: 100%;
    height: auto;
}

.doughnut-chart {
    width: 100%;
    flex-grow: 1;
}
</style>
