<script setup>
import { onMounted, ref, onBeforeUnmount, watch } from 'vue';
import * as echarts from 'echarts';

// 接收父组件传递的 props
const props = defineProps({
    chartData: {
        type: Array,
        required: true,
    },
    xAxisData: {
        type: Array,
        required: true,
    }
});

// 定义一个 ref，用于指向 DOM 元素
const lineChart = ref(null);

onMounted(() => {
    // 初始化 ECharts 实例
    const chart = echarts.init(lineChart.value);

    // 定义更新图表的方法
    const setChartOption = () => {
        // 设置 ECharts 的配置项
        const option = {
            title: {
                text: 'ECharts 折线图示例'
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: props.xAxisData
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: props.chartData,
                    type: 'line'
                }
            ]
        };
        // 将配置项应用到图表实例中
        chart.setOption(option);
    };

    // 初始化图表
    setChartOption();

    // 监听传入数据的变化并更新图表
    watch([() => props.chartData, () => props.xAxisData], setChartOption, { deep: true });

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
    <div ref="lineChart" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 自定义样式，可以根据需要调整 */
</style>
