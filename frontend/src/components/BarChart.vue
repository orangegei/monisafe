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
            // backgroundColor: '#f0f0f0',
            title: {
                text: props.title,
                left: 'center',
                top: '10%',
                textStyle: {
                    color: '#333333', // 标题颜色
                    fontSize: 16,
                    fontWeight: 'bold'
                }
            },
            grid: {
                left: '5%',
                right: '8%',
                bottom: '5%',
                top: '30%',
                containLabel: true
            },
            xAxis: {
                data: props.xAxisData,
                axisLine: {
                    show: false // 隐藏横坐标轴的线
                },
                axisTick: {
                    show: false // 隐藏横坐标轴的小竖线
                },
                axisLabel: {
                    fontStyle: 'italic' // 斜体标签
                }
            },
            yAxis: {
                axisLine: {
                    lineStyle: {
                        width: 2,
                        color: '#333'
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed' // 虚线的网格线
                    }
                }
            },
            series: [
                {
                    name: 'Sales',
                    type: 'bar',
                    data: props.chartData,
                    barWidth: '70%', // 控制柱状图的宽度
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: '#83bff6' },
                            { offset: 0.5, color: '#188df0' },
                            { offset: 1, color: '#188df0' }
                        ]),
                        borderRadius: [10, 10, 10, 10] // 圆角效果
                    }
                }
            ],
            tooltip: {
                backgroundColor: 'rgba(0, 0, 0, 0.7)',
                borderRadius: 10
            },
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
