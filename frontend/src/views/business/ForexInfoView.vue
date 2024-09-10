<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import instance from '@/utils/request';
import { ElMessage } from 'element-plus';
import { ElButton } from 'element-plus';
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

// 修改后的日期格式化函数，确保按本地时间处理
const formatDate = (date: Date): string => {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，因此+1
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

// 外汇业务数据
const forex_bar_xdata = ref([]);
const forex_bar_ydata = ref([]);
const forex_pieData = ref([]);
const forex_pieData2 = ref([]);
const forex_doughnutChartData = ref([]);
const forex_doughnutChartData2 = ref([]);
const forex_line_amount_data = ref([]);
const forex_line_count_data = ref([]);
const daysOfWeek = ref(['Mon.', 'Tue.', 'Wed.', 'Thus.', 'Fri.', 'Sat.', 'Sun.']);

// 展示分析数据
const maxTransactionRange = ref('');
const maxAmountAgeGroup = ref('');
const maxCountAgeGroup = ref('');
const maxAmountDay = ref('');
const maxCountDay = ref('');
const maxExchangePurpose = ref('');
const maxCurrencyType = ref('');

// 处理函数
// 处理外汇柱状图数据
function handleForexChartData(chartData) {
    forex_bar_xdata.value = chartData.xdata;
    forex_bar_ydata.value = chartData.ydata;

    // 找到笔数最多的金额区间
    const maxIndex = chartData.ydata.indexOf(Math.max(...chartData.ydata));
    maxTransactionRange.value = chartData.xdata[maxIndex];
}

// 处理饼状图数据
function forex_transformChartDataToPieData(chartData, dataType) {
    if(dataType === 'age') {
        forex_pieData.value = chartData.xdata.map((name, index) => ({
            name: name,
            value: chartData.ydata[index]
        }));
        // 找到交易金额最多的年龄段
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData.ydata));
        maxAmountAgeGroup.value = chartData.xdata[maxIndex];
    }
    else if (dataType === 'purpose') {
        forex_pieData2.value = chartData.xdata.map((name, index) => ({
            name: name,
            value: chartData.ydata[index]
        }));
        // 找到交易金额最多的换汇目的
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData.ydata));
        maxExchangePurpose.value = chartData.xdata[maxIndex];
    }    
}

// 处理环形图数据
function forex_transformChartDataTodChartData(chartData, dataType) {
    if(dataType === 'age') {
        forex_doughnutChartData.value = chartData.xdata.map((name, index) => ({
            name: name,
            value: chartData.ydata[index]
        }));
        // 找到交易笔数最多的年龄段
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData.ydata));
        maxCountAgeGroup.value = chartData.xdata[maxIndex];
    }
    else if (dataType === 'type') {
        forex_doughnutChartData2.value = chartData.xdata.map((name, index) => ({
            name: name,
            value: chartData.ydata[index]
        }));
        // 找到交易笔数最多的货币种类
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData.ydata));
        maxCurrencyType.value = chartData.xdata[maxIndex];
    }
}

// 处理折线图数据
function handleForexLineChartData(chartData, dataType) {
    if (dataType === 'amount') {
        forex_line_amount_data.value = chartData;

        // 找到交易金额最多的时间点
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData));
        maxAmountDay.value = daysOfWeek.value[maxIndex];
    } else if (dataType === 'count') {
        forex_line_count_data.value = chartData;

        // 找到交易笔数最多的时间点
        const maxIndex = chartData.ydata.indexOf(Math.max(...chartData));
        maxCountDay.value = daysOfWeek.value[maxIndex];
    }
}

// 获取外汇数据
const getAllData = async () => {
    try {
        const params = {
            startTime: formatDate(dateRange.value[0]),
            endTime: formatDate(dateRange.value[1]),
        };

        const requests = [
            instance.get('/business/forex/age/amount', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/age/count', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/range', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/weekAmount', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/weekCount', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/purpose', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
            instance.get('/business/forex/type', { params, headers: { Authorization: sessionStorage.getItem('token') } }),
        ];

        const responses = await Promise.all(requests);
        responses.forEach((response, index) => {
            if (response.data.code === 0) {
                const data = response.data.data;
                switch (index) {
                    case 0: forex_transformChartDataToPieData(data, 'age'); break;
                    case 1: forex_transformChartDataTodChartData(data, 'age'); break;
                    case 2: handleForexChartData(data); break;
                    case 3: handleForexLineChartData(data, 'amount'); break;
                    case 4: handleForexLineChartData(data, 'count'); break;
                    case 5: forex_transformChartDataToPieData(data, 'purpose'); break;
                    case 6: forex_transformChartDataToPieData(data, 'type'); break;
                }
            } else {
                console.error('Error in response:', response.message);
            }
        });
    } catch (error) {
        console.error('Failed to fetch Forex data', error);
    }
};

// 选择器和确认按钮逻辑
const router = useRouter();
const businessType = ref<string>('forex');

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

    const startDate = formatDate(dateRange.value[0]);
    const endDate = formatDate(dateRange.value[1]);

    sessionStorage.setItem('startTime', startDate);
    sessionStorage.setItem('endTime', endDate);

    if (businessType.value === 'forex') {
        getAllData();
    } else if (businessType.value === 'atm') {
        router.push('/business/chart/atm');
    }
}

onMounted(() => {
    const startTime = sessionStorage.getItem('startTime');
    const endTime = sessionStorage.getItem('endTime');
    if (startTime && endTime) {
        dateRange.value = [new Date(startTime), new Date(endTime)];
    }
    getAllData();
});
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

                <div class="display">
                    <div class="time-line">
                        <el-timeline style="width: 80%;">
                            <el-timeline-item timestamp="外汇交易金额与笔数柱状图" placement="top" style="height: 30vh;"
                                color="#008C8C">
                                <el-card style="height: 28vh;">
                                    <div>交易笔数最多的金额区间是：</div>
                                    <div class="number-text">{{ maxTransactionRange }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="外汇年龄段与交易金额饼状图" placement="top" style="height: 20vh;"
                                color="#E85827">
                                <el-card>
                                    <div>交易金额最多的年龄段是：</div>
                                    <div class="number-text">{{ maxAmountAgeGroup }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="外汇年龄段与交易笔数环形图" placement="top" style="height: 20vh;"
                                color="#B05923">
                                <el-card>
                                    <div>交易笔数最多的年龄段是：</div>
                                    <div class="number-text">{{ maxCountAgeGroup }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="过去一周外汇交易金额与时间折线图" placement="top" style="height: 20vh;"
                                color="#002FA7">
                                <el-card>
                                    <div>交易金额最多的时间点是：</div>
                                    <div class="number-text">{{ maxAmountDay }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="过去一周外汇交易笔数与时间折线图" placement="top" style="height: 20vh;"
                                color="#800020">
                                <el-card>
                                    <div>交易笔数最多的时间点是：</div>
                                    <div class="number-text">{{ maxCountDay }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="外汇交易笔数与换汇目的饼状图" placement="top" style="height: 20vh;"
                                color="#003153">
                                <el-card>
                                    <div>各笔交易中最多的换汇目的是：</div>
                                    <div class="number-text">{{ maxExchangePurpose }}</div>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="外汇交易金额与货币种类环形图" placement="top" style="height: 20vh;"
                                color="#E60000">
                                <el-card>
                                    <div>交易总金额中最多的货币种类是：</div>
                                    <div class="number-text">{{ maxCurrencyType }}</div>
                                </el-card>
                            </el-timeline-item>

                        </el-timeline>
                    </div>

                    <div class="chart">
                        <div class="bar-chart-row">
                            <div class="bar-chart">
                                <BarChart :chartData="forex_bar_ydata" :xAxisData="forex_bar_xdata" title="外汇交易金额与笔数柱状图" />
                            </div>
                        </div>

                        <div class="pie-doughnut-chart-row">
                            <div class="pie-chart">
                                <PieChart :chartData="forex_pieData" title="外汇年龄段与交易金额饼状图"></PieChart>
                            </div>
                            <div class="doughnut-chart">
                                <DoughnutChart :chartData="forex_doughnutChartData" title="外汇年龄段与交易笔数环形图">
                                </DoughnutChart>
                            </div>
                        </div>

                        <div class="line-chart-row">
                            <div class="line-chart">
                                <LineChart :chartData="forex_line_amount_data" :xAxisData="daysOfWeek" title="过去一周外汇交易金额与时间折线图" />
                            </div>
                            <div class="line-chart">
                                <LineChart :chartData="forex_line_count_data" :xAxisData="daysOfWeek" title="过去一周外汇交易笔数与时间折线图" />
                            </div>
                        </div>

                        <div class="pie-doughnut-chart-row">
                            <div class="pie-chart">
                                <PieChart :chartData="forex_pieData2" title="外汇交易笔数与换汇目的饼状图"></PieChart>
                            </div>
                            <div class="doughnut-chart">
                                <DoughnutChart :chartData="forex_doughnutChartData2" title="外汇交易金额与货币种类环形图">
                                </DoughnutChart>
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
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 10px;
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
    width: 100%;
    height: 40vh;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 3vh;
}

.pie-chart,
.doughnut-chart {
    flex: 1;
    width: 50%;
    border-radius: 15px;
    background-color: antiquewhite;
    box-sizing: border-box;
}

.line-chart-row {
    width: 100%;
    height: 40vh;
    display: flex;
    justify-content: space-between;
    gap: 3vh;
}

.line-chart {
    flex: 1;
    width: 50%;
    border-radius: 15px;
}
</style>
