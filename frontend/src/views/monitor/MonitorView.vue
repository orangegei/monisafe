<script setup>
import GaugeChart from '@/components/GaugeChart.vue';
import LineChart from '@/components/LineChart.vue';
import FrameView from '@/views/FrameView.vue';
import { ref, onMounted } from 'vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'; // 引入所有图标组件

const atmGaugeValue = ref(75);
const forexGaugeValue = ref(50);

// 定义 ATM 和外汇折线图的数据
const atmLineChartData = ref([120, 200, 150, 80, 70, 110, 130, 160, 180, 140]);
const forexLineChartData = ref([100, 200, 120, 70, 90, 50, 110, 180, 130, 140]);

// 定义 X 轴数据
const xAxisData = ref(['-10m', '-9m', '-8m', '-7m', '-6m', '-5m', '-4m', '-3m', '-2m', '-1m']);

// 定义监控告警内容
const alerts = ref([
    { time: '10:00', message: 'ATM响应时间过长' },
    { time: '10:05', message: '外汇响应时间过长' },
    { time: '10:10', message: 'ATM时间金额异常' },
    { time: '10:15', message: '外汇时间金额异常' },
    { time: '10:20', message: '正常' },
]);

const getAlertIcon = (message) => {
    if (message.includes('响应时间过长')) {
        return '❌'; // 叉图标
    } else if (message.includes('金额异常')) {
        return '❗'; // 感叹号图标
    } else {
        return '✔'; // 钩图标
    }
};
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="main-body-container">
                <!-- 监控部分 -->
                <div class="left-section">
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
                                                <el-col :span="20">
                                                    <el-statistic title="交易金额/元" :value="4475" />
                                                    <template #suffix>
                                                        <span class="custom-suffix">元</span>
                                                    </template>
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                        <el-card class="statistic-card">
                                            <el-row class="statistic-row bottom-statistic">
                                                <el-col :span="24">
                                                    <el-statistic title="交易笔数/笔" :value="5345" />
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                    </div>
                                </div>
                            </el-card>

                            <el-card class="chart-card line-chart-card" shadow="hover">
                                <div class="chart-title">过去10分钟ATM 取款响应时间</div>
                                <LineChart :chartData="atmLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100%; height: 220%;"/>
                            </el-card>
                        </div>

                        <!-- 外汇监控部分 -->
                        <div class="forex-container">
                            <el-card class="chart-card line-chart-card" shadow="hover">
                                <div class="chart-title">过去10分钟外汇交易响应时间</div>
                                <LineChart :chartData="forexLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100%; height: 220%;"/>
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
                                                    <el-statistic title="交易金额/元" :value="453" />
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                        <el-card class="statistic-card">
                                            <el-row class="statistic-row bottom-statistic">
                                                <el-col :span="24">
                                                    <el-statistic title="交易笔数/笔" :value="53545" />
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                    </div>
                                </div>
                            </el-card>
                        </div>
                    </div>
                </div>

                <!-- 告警部分 -->
                <div class="right-section">
                    <div class="alerts-container">
                        <el-card class="chart-card alert-card" shadow="hover">
                            <div class="chart-title">实时告警信息</div>
                            <el-table :data="alerts" stripe>
                                <el-table-column prop="time" label="时间" width="65" />
                                <el-table-column prop="message" label="告警内容" width="140" class-name="center-align" />
                                <el-table-column label=" " width="50">
                                    <template #default="{ row }">
                                        <div class="alert-icon">
                                            <i v-if="getAlertIcon(row.message) === '✔'" class="el-icon-check"></i>
                                            <i v-if="getAlertIcon(row.message) === '❗'" class="el-icon-warning"></i>
                                            <i v-if="getAlertIcon(row.message) === '❌'" class="el-icon-close"></i>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-card>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.main-body-container {
    display: flex;
    justify-content: space-between;
}

.alerts-container {
    padding-top: 1%;
    height: 90%;
}

.alert-card {
    height: calc(91%);
    display: flex;
    flex-direction: column;
    margin-left: -15%;
    margin-right: 10%;
}

.left-section {
    flex: 2;
}

.right-section {
    flex: 1;
    align-items: stretch;
}

.monitor-container {
    padding-top: 1%;
    display: flex;
    flex-direction: column;
    /* gap: 10px;   */
    margin-left: 2%;
    margin-right: 2%;
}

.atm-container {
    display: grid;
    grid-template-columns: 60% 70%; 
    margin-bottom: -10%;
    gap: 25px;
}

.forex-container {
    display: grid;
    grid-template-columns: 70% 60%; 
    gap: 25px;
}

.chart {
    width: 100%;
    height: 100%;
}

.chart-card {
    border-radius: 15px;
    padding: 4%;
}

.gauge-card {
    height: 66%;
}   

.line-chart-card {
    height: 65%;
}

.chart-title {
    font-size: 18px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 6%;
    margin-top: -5%;
}

.chart-content {
    display: flex;
    align-items: stretch;
}

.left-section {
    display: flex;
    flex-direction: row;
    flex: 1;
    justify-content: flex-start;
    align-items: center;
}

.right-section {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-end;
    margin-right: -15px;
}

.right-section > *:not(:last-child) {
    margin-bottom: 25%; 
}

.statistic-card {
    border-radius: 15px;
    background-color: #eff3f8;
    width: 68%;
    height: 17%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 8%;
}

.statistic-row {
    width: 150%;
}

.el-statistic .custom-suffix .el-statistic__suffix {
    font-size: 0.6em;
}

.custom-suffix {
  font-size: 0.6em;
  vertical-align: baseline;
  margin-left: 2px;
}

.center-align .cell {
    text-align: center;
}

.center-align .header {
    text-align: center;
}

.alert-icon {
    text-align: center;
}

.el-icon-check {
    color: green;
}

.el-icon-warning {
    color: orange;
}

.el-icon-close {
    color: red;
}
</style>
