<script setup>
import GaugeChart from '@/components/GaugeChart.vue';
import LineChart from '@/components/LineChart.vue';  // 导入封装好的折线图组件
import { ref, onMounted } from 'vue';
import FrameView from '@/views/FrameView.vue';

const atmGaugeValue = ref(75);
const forexGaugeValue = ref(50);

// 定义 ATM 和外汇折线图的数据
const atmLineChartData = ref([120, 200, 150, 80, 70, 110, 130, 160, 180, 140]);
const forexLineChartData = ref([100, 200, 120, 70, 90, 50, 110, 180, 130, 140]);

// 定义 X 轴数据
const xAxisData = ref(['-10m', '-9m', '-8m', '-7m', '-6m', '-5m', '-4m', '-3m', '-2m', '-1m']);

// 定义监控告警内容
const alerts = ref([
    { time: '10:00', message: 'ATM响应时间过长', level: '✔' },
    { time: '10:05', message: '外汇交易金额异常', level: '❗' },
    { time: '10:10', message: 'ATM取款失败', level: '❌' },
]);

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
                                                <el-col :span="24">
                                                    <el-statistic title="交易金额" :value="445" suffix="元" class="custom-suffix" />
                                                    <template #suffix>
                                                        <span class="custom-suffix">元</span>
                                                    </template>
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                        <el-card class="statistic-card">
                                            <el-row class="statistic-row bottom-statistic">
                                                <el-col :span="24">
                                                    <el-statistic title="交易笔数" :value="5345" suffix="笔" class="custom-suffix" />
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                    </div>
                                </div>
                            </el-card>

                            <el-card class="chart-card line-chart-card" shadow="hover">
                                <div class="chart-title">过去10分钟ATM 取款响应时间</div>
                                <LineChart :chartData="atmLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100%; height: 230%;"/>
                            </el-card>
                        </div>

                        <!-- 外汇监控部分 -->
                        <div class="forex-container">
                            <el-card class="chart-card line-chart-card" shadow="hover">
                                <div class="chart-title">过去10分钟外汇交易响应时间</div>
                                <LineChart :chartData="forexLineChartData" :xAxisData="xAxisData" :showTitle="false" style="width: 100%; height: 230%;"/>
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
                                                    <el-statistic title="交易金额" :value="4545" suffix="元" class="custom-suffix" />
                                                </el-col>
                                            </el-row>
                                        </el-card>
                                        <el-card class="statistic-card">
                                            <el-row class="statistic-row bottom-statistic">
                                                <el-col :span="24">
                                                    <el-statistic title="交易笔数" :value="5345" suffix="笔" class="custom-suffix" />
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
                                <el-table-column prop="level" label=" " width="45" />
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
    padding: 10px;
}

.alerts-container {
    padding: 5px;
    margin-left: 20px;
    height: 100%;
}

.alert-card {
    height: calc(100% - 30px); /* 减去 padding 和 margin */
    display: flex;
    flex-direction: column;
}

.left-section {
    flex: 2;
}

.right-section {
    flex: 1;
    align-items: stretch
}

.monitor-container {
    padding: 5px;
    display: flex;
    flex-direction: column;
    gap: 40px; /* 分隔ATM和外汇部分 */
    margin-left: 20px;
    margin-right: 20px;
}

.atm-container {
    display: grid;
    grid-template-columns: 330px 410px; 
    gap: 20px;
}

.forex-container {
    display: grid;
    grid-template-columns: 410px 330px; 
    gap: 20px;
}

.chart {
    width: 100%;
    height: 200px;
}

.chart-card {
    border-radius: 15px;
    padding: 17px;
}

.gauge-card {
    height: 225px;
}   

.line-chart-card {
    height: 225px;
}

.chart-title {
    font-size: 18px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
    margin-top: -20px;
}

.chart-content {
    display: flex;
    align-items: stretch;
}

.left-section {
    display: flex;
    flex-direction: row;
    flex: 1;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-left: -20px;
}

.right-section {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-end;
    margin-right: -15px;
}

.right-section > *:not(:last-child) {
    margin-bottom: 40px; 
}

.statistic-card {
    border-radius: 15px;
    margin-bottom: 10px;
    background-color: #eff3f8;
    width: 100px; 
    height: 45px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px;
}

.statistic-row {
    width: 150%;
}

.el-statistic .custom-suffix .el-statistic__suffix {
    font-size: 0.6em
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
</style>
