<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
import instance from '@/utils/request';
import { ElMessage } from 'element-plus';
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
    today,
]);

// 当用户在日期选择器中选择新的日期范围时
function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

// 将日期转换为字符串
const formatDate = (date: Date): string => {
    return date.toISOString().split('T')[0];
};

// ATM业务数据
const ATM_bar_xdata = ref([]);
const ATM_bar_ydata = ref([]);
const ATM_pieData = ref([]);
const ATM_doughnutChartData = ref([]);

// 处理函数
function handleATMChartData(chartData) {
    ATM_bar_xdata.value = chartData.xdata;
    ATM_bar_ydata.value = chartData.ydata;
}

function ATM_transformChartDataToPieData(chartData) {
    ATM_pieData.value = chartData.xdata.map((name, index) => ({
        name: name,
        value: chartData.ydata[index]
    }));
}

function ATM_transformChartDataTodChartData(chartData) {
    ATM_doughnutChartData.value = chartData.xdata.map((name, index) => ({
        name: name,
        value: chartData.ydata[index]
    }));
}

// 获取ATM数据
const getAllData = async () => {
    try {
        const params = {
            startTime: formatDate(dateRange.value[0]),
            endTime: formatDate(dateRange.value[1]),
        };

        const requests = [
            instance.get('/business/atm/age/amount', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/atm/age/count', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/atm/range', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
        ];

        const responses = await Promise.all(requests);
        responses.forEach((response, index) => {
            if (response.data.code === 0) {
                const data = response.data.data;
                switch (index) {
                    case 0: ATM_transformChartDataToPieData(data); break;
                    case 1: ATM_transformChartDataTodChartData(data); break;
                    case 2: handleATMChartData(data); break;
                }
            } else {
                console.error('Error in response:', response.message);
            }
        });
    } catch (error) {
        console.error('Failed to fetch ATM data', error);
    }
};

// 选择器和确认按钮逻辑
const router = useRouter();
const businessType = ref<string>('atm');
function handleTypeChange(type: string) {
    businessType.value = type;
}
function validateSelections() {
    if (!dateRange.value || !businessType.value) {
        ElMessage.error('日期或业务内容不能为空');
        return false;
    }
    return true;
}

function handleConfirmClick() {
    if (!validateSelections())
        return;

    const startTime = formatDate(dateRange.value[0]);
    const endTime = formatDate(dateRange.value[1]);

    if (businessType.value === 'atm') {
        getAllData();
    } else if (businessType.value === 'forex') {
        router.push({ path: '/business/chart/forex', query: { startTime, endTime } });
    }
}

onMounted(() => {
    getAllData();
});

// 柱状图数据
// const barData = ref([120, 60, 150, 80, 100, 130, 110, 50]);
// const categories = ref(['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']);

// const lineData = ref([120, 200, 150, 80, 70, 110, 130]);
// const daysOfWeek = ref(['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']);
// const pieData = ref([
//     { value: 1048, name: 'Search Engine' },
//     { value: 735, name: 'Direct' },
//     { value: 580, name: 'Email' },
//     { value: 484, name: 'Union Ads' },
//     { value: 300, name: 'Video Ads' },
// ]);
// const doughnutChartData = ref([
//     { value: 1048, name: 'Search Engine' },
//     { value: 735, name: 'Direct' },
//     { value: 580, name: 'Email' },
//     { value: 484, name: 'Union Ads' },
//     { value: 300, name: 'Video Ads' }
// ]);
// const timelineItems = ref([
//     { text: 'ATM交易金额占比最多的年龄段是', color: '#ebe5e5' },
//     { text: '金额为xxxx范围的交易笔数最多', color: '#DDE8F2' },
//     { text: 'ATM交易笔数占比最多的年龄段是', color: '#cdeded' },
//     { text: '本周中ATM交易金额最多的是', color: '#e0f4fe' },
//     { text: '本周中ATM交易笔数最多的是', color: '#e0f4fe' },
// ]);

</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="picker">
                    <div class="date-picker-wrapper">
                        <DatePicker :time="dateRange" @update:internalValue="handleDateChange"
                            :disabled-date="disableFutureDates"></DatePicker>
                    </div>
                    <div class="type-selector">
                        <SelectIcon v-model="businessType" @update:internalValue="handleTypeChange" />
                    </div>
                    <ElButton type="primary" class="confirm-button" @click="handleConfirmClick">确认</ElButton>
                </div>

                <!-- 时间线和图表 -->
                <div class="display">
                    <div class="time-line">
                        <el-timeline style="width: 80%;">
                            <el-timeline-item timestamp="交易金额与笔数柱状图" placement="top" style="height: 30vh;"
                                color="#E85827">
                                <el-card style="height: 28vh;">
                                    <div>交易笔数最多的金额区间是：</div>
                                    <div class="number-text">￥0~5000</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="年龄段与交易金额饼状图" placement="top" style="height: 20vh;"
                                color="#E85827">
                                <el-card>
                                    <div>交易金额最多的年龄段是：</div>
                                    <div class="number-text">20~30</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="年龄段与交易笔数环形图" placement="top" style="height: 20vh;"
                                color="#E85827">
                                <el-card>
                                    <div>交易笔数最多的年龄段是：</div>
                                    <div class="number-text">30~40</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="交易金额与时间折线图" placement="top" style="height: 20vh;"
                                color="#E85827">
                                <el-card>
                                    <div>交易金额最多的时间点是：</div>
                                    <div class="number-text">周三</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="交易笔数与时间折线图" placement="top" style="height: 20vh;"
                                color="#E85827">
                                <el-card>
                                    <div>交易笔数最多的时间点是：</div>
                                    <div class="number-text">周五</div>
                                </el-card>
                            </el-timeline-item>
                        </el-timeline>
                    </div>

                    <div class="chart">
                        <div class="bar-chart-row">
                            <div class="bar-chart">
                                <BarChart :chartData="barData" :xAxisData="categories" title="ATM交易金额对应笔数" />
                            </div>
                        </div>

                        <div class="pie-doughnut-chart-row">
                            <div class="pie-chart">
                                <PieChart :chartData="ATM_pieData" title="各年龄ATM总交易金额占比"></PieChart>
                            </div>
                            <div class="doughnut-chart">
                                <DoughnutChart :chartData="ATM_doughnutChartData" title="各年龄段ATM总交易笔数占比">
                                </DoughnutChart>
                            </div>
                        </div>

                        <div class="line-chart-row">
                            <div class="line-chart">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" title="ATM时间段交易金额趋势" />
                            </div>
                            <div class="line-chart">
                                <LineChart :chartData="lineData" :xAxisData="daysOfWeek" title="ATM时间段交易笔数趋势" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
/* 容器布局 */
.container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 3vh;
}

/* 选择器布局 */
.picker {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    gap: 3vw;
}

.confirm-button {
    border-radius: 10px;
}

/* 时间线和图表的布局 */
.display {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: stretch;
    /* 确保整个页面占满视口 */
}

/* 时间线部分的样式 */
.time-line {
    width: 30%;
}

.number-text {
    font-family: 'FZZJ LongYTWJ', sans-serif;
    font-size: 36px;
    line-height: 2.5;
}

/* 图表部分的样式 */
.chart {
    width: 70%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 4vh;
}

.bar-chart-row {
    width: 100%;
    height: 30vh;
}

.bar-chart {
    flex: 1;
    height: 100%;
    border-radius: 15px;
    background-color: #ebe5e5;
}

.pie-doughnut-chart-row {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    gap: 3vh;
}

.pie-chart,
.doughnut-chart {
    width: 50%;
    border-radius: 15px;
    height: 40vh;
    background-color: antiquewhite;
    box-sizing: border-box;
}

.line-chart-row {
    display: flex;
    justify-content: space-between;
    width: 100%;
    gap: 3vh;
}

.line-chart {
    width: 50%;
    border-radius: 15px;
    height: 40vh;
}
</style>
