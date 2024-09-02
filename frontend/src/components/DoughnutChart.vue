<script setup>
import { onMounted, ref, watch, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

// 接收父组件传递的 props
const props = defineProps({
    chartData: {
        type: Array,
        required: true,
    },
    title: {
        type: String,
        default: 'ECharts Doughnut Chart'
    },
});

// 定义一个 ref，用于指向 DOM 元素
const chartRef = ref(null);

onMounted(() => {
    // 初始化 ECharts 实例
    const chart = echarts.init(chartRef.value);

    // 定义更新图表的方法
    const setChartOption = () => {
        const option = {
            title: {
                text: props.title,
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                show: false,
            },
            series: [
                {
                    name: 'Data',
                    type: 'pie',
                    radius: ['40%', '70%'], // 设置内外半径，形成环形图
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: props.chartData
                }
            ]
        };
        chart.setOption(option);
    };

    // 初始化图表
    setChartOption();

    // 监听传入数据的变化并更新图表
    watch(() => props.chartData, setChartOption, { deep: true });

    // 监听窗口尺寸变化，自动调整图表大小
    window.addEventListener('resize', chart.resize);

    // 组件卸载前销毁 ECharts 实例
    onBeforeUnmount(() => {
        window.removeEventListener('resize', chart.resize);
        chart.dispose();
    });
});
</script>

<template>
    <div ref="chartRef" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 根据需要调整样式 */
</style>
