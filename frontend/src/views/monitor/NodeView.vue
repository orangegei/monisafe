<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import FrameView from '@/views/FrameView.vue';
import { ElTable, ElTableColumn, ElCard, ElMessage } from 'element-plus';
import axios from 'axios';
import instance from '@/utils/request'

const chartRef = ref(null);
const serverData = ref([]);
const suggestions = ref([]);

// 获取后端数据
const fetchData = async () => {
    try {
        const response = await instance.get('/monitor/node', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });

        if (response.data.code === 0) {
            const data = response.data.data; // 假设返回的数据格式是 NodeData 数组

            console.log(data);

            // 处理每个节点数据，计算最大值和平均值
            const processedData = data.map(nodeData => {
                const maxResponse = Math.max(...nodeData.averageResponseTime).toFixed(2) + 'ms';
                const avgResponse = (nodeData.averageResponseTime.reduce((sum, time) => sum + time, 0) / nodeData.averageResponseTime.length).toFixed(2) + 'ms';

                // 检查是否持续3天以上超过170ms
                const overThreshold = nodeData.averageResponseTime.filter(time => time > 170).length >= 3;
                
                // 如果超过阈值，添加建议
                if (overThreshold) {
                    suggestions.value.push({
                        name: nodeData.nodeName,
                        action: '建议转变为分布式系统'
                    });
                }
                return {
                    name: nodeData.nodeName,
                    maxResponse,
                    avgResponse
                };
            });
            serverData.value = processedData;
            // 更新图表数据
            updateChart(data);
        }
    } catch (error) {
        console.error('Error:', error);
        ElMessage.error('获取数据失败');
    }
};


// 更新图表
const updateChart = (data) => {
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
            data: ['ATMServer', 'FXServer', 'FXCoreEntry', 'CoreService'],
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
            data: ['6天前', '5天前', '4天前', '3天前', '2天前', '1天前', '今天']
        },
        yAxis: {
            type: 'value',
            name: '响应时间 (ms)'
        },
        series: data.map(node => ({
            name: node.nodeName,
            type: 'bar',
            barWidth: '15%',
            emphasis: { focus: 'series' },
            data: node.averageResponseTime.map(value => parseFloat(value.toFixed(2)))
        })),

        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        }
    };

    myChart.setOption(option);
};

onMounted(() => {
    fetchData();
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
                    <div class="content">
                        <div style="text-align: center; font-size: 18px; font-weight: bold; padding: 10px;">
                            Table: 各个节点响应时间
                        </div>
                        <el-table :data="serverData" border style="width: 100%">
                            <el-table-column prop="name" label="节点名称" align="center"></el-table-column>
                            <el-table-column prop="maxResponse" label="最大响应时间" align="center"></el-table-column>
                            <el-table-column prop="avgResponse" label="平均响应时间" align="center"></el-table-column>
                        </el-table>
                    </div>

                    <!-- 右侧显示建议内容 -->
                    <div class="propose">
                        <el-card>
                            <p class="suggestion-description">平均响应时间持续超过 <span class="number">阈值 3天</span> 的节点：</p>
                            <div v-for="suggestion in suggestions" :key="suggestion.name">
                                <p class="suggestion-node">{{ suggestion.name }}</p>
                                <p class="suggestion-action">{{ suggestion.action }}</p>
                            </div>
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


.suggestion-node {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
    font-size: 28px;
    text-align: center;
}

.suggestion-action {
    text-align: right;
    font-size: 32px;
    font-weight: 600;
}

.number {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
    font-size: 24px;
}

.text {
    font-weight: 400;
    font-style: normal;
    font-size: 24px;
}
</style>