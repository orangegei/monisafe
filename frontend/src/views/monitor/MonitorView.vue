<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import instance from '@/utils/request'
import GaugeChart from '@/components/GaugeChart.vue';
import LineChart from '@/components/LineChart.vue';
import FrameView from '@/views/FrameView.vue';
import severeIcon from '@/assets/severe.svg';
import warningIcon from '@/assets/warning.svg';

// 实时数据状态
const atmGaugeValue = ref(0);
const forexGaugeValue = ref(0);
const atmAmount = ref(0);
const atmCount = ref(0);
const forexAmount = ref(0);
const forexCount = ref(0);

// X轴数据
const xAxisData = ref([]);

// 告警数据
// const alerts = ref([
//     { time: '09:01:20', message: 'ATM响应时间过长', status: 'severe' },
//     { time: '09:01:30', message: '外汇交易金额异常', status: 'warning' },
//     { time: '09:01:49', message: 'ATM正常运行', status: 'severe' },
// ]);

const atmLineChartData = ref([]);
const forexLineChartData = ref([]);
const alerts = ref([]);


// 获取ATM数据的函数
const fetchAtmData = async () => {
    try {
        const response = await instance.get('/monitor/atm', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code === 0) {
            const data = response.data.data;
            atmGaugeValue.value = data[9].averageResponseTime; // 更新仪表盘值
            atmLineChartData.value = data.map(item => item.averageResponseTime); // 更新折线图数据
            xAxisData.value = data.map(item => item.time); // 更新X轴数据
            atmAmount.value = data[9].transactionAmount; // 更新交易金额
            atmCount.value = data[9].transactionCount; // 更新交易笔数

            xAxisData.value = data.map(item => {
                const date = new Date(item.transactionTime);
                return `${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
            }); // 更新x轴数据
        }
    } catch (error) {
        console.error('Failed to fetch ATM data', error);
    }
};

// 获取Forex数据的函数
const fetchForexData = async () => {
    try {
        const response = await instance.get('/monitor/forex', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code === 0) {
            const data = response.data.data;
            forexGaugeValue.value = data[9].averageResponseTime; // 更新仪表盘值
            forexLineChartData.value = data.map(item => item.averageResponseTime); // 更新折线图数据
            // xAxisData.value = data.map(item => item.time);
            forexAmount.value = data[9].transactionAmount; // 更新交易金额
            forexCount.value = data[9].transactionCount; // 更新交易笔数
        }
    } catch (error) {
        console.error('Failed to fetch Forex data', error);
    }
};

// 获取告警数据的函数
const fetchAlerts = async () => {
    try {
        const response = await instance.get('/monitor/logs', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code === 0) {
            // console.log('success to fetch alerts data');   
            // console.log('alerts data:', alerts.value);
            const data = response.data.data; // 更新告警数据
            alerts.value = data.map(data => ({
                time: data.transactionTime,
                message: data.eventType,
                status: data.status,
            }));
            console.log(alerts.value);

        }
    } catch (error) {
        console.error('Failed to fetch alerts data', error);
    }
};

// 定时获取数据
onMounted(() => {
    fetchAtmData();
    fetchForexData();
    fetchAlerts();
    setInterval(() => {
        fetchAtmData();
        fetchForexData();
        fetchAlerts();
    }, 60000); // 每分钟请求一次
});
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="main-body-container">

                <!-- 监控部分 -->
                <div class="monitor-container">

                    <!-- ATM监控部分 -->
                    <div class="atm-container">
                        <el-card class="chart-card gauge-card" shadow="hover">
                            <div class="chart-title">ATM 实时数据</div>
                            <div class="chart-content">
                                <div class="left-section">
                                    <GaugeChart :value="atmGaugeValue" />
                                </div>
                                <div class="right-section">
                                    <el-card class="statistic-card">
                                        <el-row class="statistic-row top-statistic">
                                            <el-col :span="24">
                                                <el-statistic title="交易金额/元" :value="atmAmount" />
                                                <template #suffix>
                                                    <span class="custom-suffix">元</span>
                                                </template>
                                            </el-col>
                                        </el-row>
                                    </el-card>
                                    <el-card class="statistic-card">
                                        <el-row class="statistic-row bottom-statistic">
                                            <el-col :span="24">
                                                <el-statistic title="交易笔数/笔" :value="atmCount" />
                                            </el-col>
                                        </el-row>
                                    </el-card>
                                </div>
                            </div>
                        </el-card>

                         <el-card class="chart-card line-chart-card" shadow="hover">
                            <div class="chart-title">过去10分钟ATM取款响应时间</div>
                            <LineChart :chartData="atmLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100% ; height: 250%;" />
                        </el-card>
                    </div>

                    <!-- 外汇监控部分 -->
                    <div class="forex-container">
                        <el-card class="chart-card line-chart-card" shadow="hover">
                            <div class="chart-title">过去10分钟外汇交易响应时间</div>
                            <LineChart :chartData="forexLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100% ; height: 250%;"/>
                        </el-card>

                        <el-card class="chart-card gauge-card" shadow="hover">
                            <div class="chart-title">外汇实时数据</div>
                            <div class="chart-content">
                                <div class="left-section">
                                    <GaugeChart :value="forexGaugeValue" />
                                </div>
                                <div class="right-section">
                                    <el-card class="statistic-card">
                                        <el-row class="statistic-row top-statistic">
                                            <el-col :span="24">
                                                <el-statistic title="交易金额/元" :value="forexAmount" />
                                                <template #suffix>
                                                    <span class="custom-suffix">元</span>
                                                </template>           
                                            </el-col>
                                        </el-row>
                                    </el-card>
                                    <el-card class="statistic-card">
                                        <el-row class="statistic-row bottom-statistic">
                                            <el-col :span="24">
                                                <el-statistic title="交易笔数/笔" :value="forexCount" />
                                            </el-col>
                                        </el-row>
                                    </el-card>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </div>

                <!-- 告警部分 -->
                <div class="alerts-container">
                    <el-card class="chart-card alert-card" shadow="hover">
                        <div class="chart-title">实时告警信息</div>
                        <el-table :data="alerts" stripe>
                            <el-table-column prop="alerts.value.time" label="时间" width="90" />
                            <el-table-column prop="alerts.value.message" label="告警内容" width="150" class-name="alerts-label" />
                            <el-table-column prop="alerts.value.status" label="状态" width="60">
                                <template #default="scope">
                                    <img v-if="scope.row.status === 'severe'" src="@/assets/severe.svg" alt="严重告警" class="alert-icon" />
                                    <img v-if="scope.row.status === 'warning'" src="@/assets/warning.svg" alt="警告" class="alert-icon" />
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-card>
                </div>a
            </div>
        </template>
    </FrameView>
</template>


<style scoped>
.main-body-container {
    display: flex;
    width: 100%;
    height: 100%;
}

.monitor-container {
    flex:2;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.alerts-container {
    flex:1;
    height: 100%;
}

.atm-container {
    display: flex;
    flex:1;
    height: 50%;
    width: 100%;
    gap: 3%;
}

.forex-container {
    display: flex;
    flex:1;
    height: 50%;
    width: 100%;
    gap: 3%;
}

.alert-card {
    height: 95%;
    margin-right: 5%;
    margin-left: 5%;
}

.left-section {
    display: flex;
    flex-direction: row;
    flex: 2;
    justify-content: flex-start;
    align-items: center;
}

.right-section {
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-end;
}

.right-section > *:not(:last-child) {
    margin-top: 20%;
    margin-bottom: 50%;
}

.chart {
    width: 100%;
    height: 100%;
}

.chart-card {
    border-radius: 15px;
    /* padding: 4%; */
}

.gauge-card {
    flex:3;
    height: 90%;
}   

.line-chart-card {
    flex:4;
    height: 90%;
}

.chart-title {
    font-size: 18px;
    font-weight: bold;
    text-align: center;
}

.chart-content {
    display: flex;
    align-items: stretch;
}

.statistic-card {
    border-radius: 15px;
    background-color: #eff3f8;
    width: 120%;
    height: 25%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.statistic-row {
    width: 100%;
}

.alerts-label {
    text-align: center;
}

.alerts-label .cell {
    text-align: center;
}

.center-align .header {
    text-align: center;
}

.alert-icon {
    width: 60%;
    height: 60%;
    display: block;
    margin: 0 auto;
}
</style>
