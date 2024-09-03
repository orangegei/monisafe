<script setup>
import { onMounted, ref, watch, onBeforeUnmount } from 'vue';
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
    },
    title: {
        type: String,
        default: 'ECharts Example Bar Chart'
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
                text: props.title
            },
            color: ['#3398DB'],
            tooltip: {},
            xAxis: {
                data: props.xAxisData,
                axisLabel: {
                    rotate: -30 // 旋转标签
                }
            },
            yAxis: {
                axisLine: {
                    lineStyle: {
                        width: 2 // 将 Y 轴线宽度设为 2 像素
                    }
                }
            },
            series: [
                {
                    name: 'Sales',
                    type: 'bar',
                    data: props.chartData,
                    barWidth: '50%', // 设置柱宽
                    animationDuration: 5000, // 动画时长
                    animationEasing: 'elasticOut', // 动画效果
                    itemStyle: {
                        borderRadius: [5, 5, 5, 5] // 设置柱子的圆角，顺序为 [左上, 右上, 右下, 左下]
                    }
                }
            ]
        };
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
    <div ref="chartRef" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 可根据需要调整样式 */
</style>
