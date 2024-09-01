<template>
    <div ref="chart" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

// 接收父组件传递的 props
const props = defineProps({
    chartData: {
        type: Array,
        required: true,
    },
});

const chart = ref(null);

onMounted(() => {
    const myChart = echarts.init(chart.value);
    const option = {
        title: {
            text: 'Sample Pie Chart',
            left: 'center',
        },
        tooltip: {
            trigger: 'item',
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                name: 'Access Source',
                type: 'pie',
                radius: '50%',
                data: props.chartData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                    },
                },
            },
        ],
    };
    myChart.setOption(option);
});
</script>

<style scoped>
/* 您可以在这里添加自定义样式 */
</style>