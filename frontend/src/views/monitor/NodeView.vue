<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import FrameView from '@/views/FrameView.vue';
import { ElTable, ElTableColumn, ElCard } from 'element-plus';

const chartRef = ref(null);

const serverData = ref([
    { name: 'ATMServer', maxResponse: '390ms', avgResponse: '320ms' },
    { name: 'FXServer', maxResponse: '290ms', avgResponse: '250ms' },
    { name: 'CoreEntry', maxResponse: '410ms', avgResponse: '260ms' },
    { name: 'CoreServer', maxResponse: '330ms', avgResponse: '180ms' }
]);

onMounted(() => {
    const myChart = echarts.init(chartRef.value);

    const option = {
        title: {
            text: '过去7天四个节点响应时间对比',
            left: 'center',
            textStyle: {
                fontSize: 18,
                fontWeight: 'bold'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['ATMServer', 'FXServer', 'CoreEntry', 'CoreServer'],
            right: '5%',
            top: '10%'
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {
                    show: true,
                    readOnly: false,
                    optionToContent: function (opt) {
                        let axisData = opt.xAxis[0].data;
                        let series = opt.series;
                        let table = '<table style="width:100%;text-align:center"><tbody><tr>'
                            + '<td>Time</td>'
                            + '<td>' + series[0].name + '</td>'
                            + '<td>' + series[1].name + '</td>'
                            + '<td>' + series[2].name + '</td>'
                            + '<td>' + series[3].name + '</td>'
                            + '</tr>';
                        for (let i = 0; i < axisData.length; i++) {
                            table += '<tr>'
                                + '<td>' + axisData[i] + '</td>'
                                + '<td>' + series[0].data[i] + '</td>'
                                + '<td>' + series[1].data[i] + '</td>'
                                + '<td>' + series[2].data[i] + '</td>'
                                + '<td>' + series[3].data[i] + '</td>'
                                + '</tr>';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
                },
                saveAsImage: { show: true }
            }
        },
        xAxis: {
            type: 'category',
            axisTick: { show: false },
            data: ['6 days ago', '5 days ago', '4 days ago', '3 days ago', '2 days ago', '1 day ago', 'Today']
        },
        yAxis: {
            type: 'value',
            name: '响应时间 (ms)'
        },
        series: [
            {
                name: 'ATMServer',
                type: 'bar',
                barWidth: '15%',
                emphasis: { focus: 'series' },
                data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
                name: 'FXServer',
                type: 'bar',
                barWidth: '15%',
                emphasis: { focus: 'series' },
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: 'CoreEntry',
                type: 'bar',
                barWidth: '15%',
                emphasis: { focus: 'series' },
                data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
                name: 'CoreServer',
                type: 'bar',
                barWidth: '15%',
                emphasis: { focus: 'series' },
                data: [98, 77, 101, 99, 40, 330, 120]
            }
        ],
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        }
    };

    myChart.setOption(option);
});
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">

                <!-- 显示柱状图 -->
                <div class="barchart">
                    <div ref="chartRef" style="width: 100%; height: 100%;"></div>
                </div>

                <div class="bottom-row">
                    <!-- 左侧显示服务节点的最大响应时间和平均响应时间 -->
                    <div class="content">
                        <el-card>
                            <el-table :data="serverData" border style="width: 100%">
                                <el-table-column prop="name" label="节点名称" align="center"></el-table-column>
                                <el-table-column prop="maxResponse" label="最大响应时间" align="center"></el-table-column>
                                <el-table-column prop="avgResponse" label="平均响应时间" align="center"></el-table-column>
                            </el-table>
                        </el-card>
                    </div>

                    <!-- 右侧显示建议内容 -->
                    <div class="propose">
                        <el-card>
                            <p>建议：</p>
                            <p>平均响应时间超过 <span class="number">180ms</span> 的节点：</p>
                            <p>CoreServer 系统架构建议转变为分布式。</p>
                        </el-card>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 98%;
    display: flex;
    flex-direction: column;
}

.barchart {
    width: 100%;
    height: 60%;
}

.bottom-row {
    display: flex;
    width: 100%;
    height: 40%;
}

.el-table {
    background-color: transparent;
}

.el-table__body tr {
    background-color: transparent;
}

.el-table__body td {
    background-color: transparent;
}

.content {
    flex: 6;
    margin-right: 10px;
    height: 100%;
    position: relative;
    width: 100%;
    padding: 10px;
    background-color: #fff;
    border: 1px solid #ddd;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 15px;
    position: relative;
    background: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.propose {
    flex: 4;
    height: 100%;
    margin-left: 10px;
    position: relative;
    width: 100%;
    padding: 10px;
    background-color: #fff;
    border: 1px solid #ddd;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 15px;
    position: relative;
    background: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.number {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
    font-size: 28px;
}
</style>