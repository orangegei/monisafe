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
    },
    title: {
        type: String,
        default: ''
    },
});

// 定义一个 ref，用于指向 DOM 元素
const lineChart = ref(null);

onMounted(() => {
    // 初始化 ECharts 实例
    const chart = echarts.init(lineChart.value);

    // 定义更新图表的方法
    const setChartOption = () => {
        const option = {
            title: {
                text: props.title,
                left: 'center',
                textStyle: {
                    color: '#333333', // 标题颜色
                    fontSize: 18,
                    fontWeight: 'bold'
                },
            },
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(50, 50, 50, 0.7)', // 半透明背景
                borderWidth: 0,
                textStyle: {
                    color: '#ffffff'
                }
            },
            xAxis: {
                type: 'category',
                axisLabel: {
                    interval: 0,
                    color: '#333333', // X轴标签颜色
                    fontWeight: 'lighter'
                },
                axisLine: {
                    lineStyle: {
                        color: '#dddddd' // X轴线颜色
                    }
                },
                data: props.xAxisData
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    color: '#333333', // Y轴标签颜色
                    fontWeight: 'lighter'
                },
                axisLine: {
                    lineStyle: {
                        color: '#dddddd' // Y轴线颜色
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: '#eeeeee' // 网格线颜色
                    }
                }
            },
            series: [
                {
                    data: props.chartData,
                    type: 'line',
                    smooth: true, // 平滑曲线
                    symbol: 'circle', // 圆形点
                    symbolSize: 8, // 点的大小
                    itemStyle: {
                        color: '#00aaff', // 折线点颜色
                        borderColor: '#ffffff', // 外圈颜色
                        borderWidth: 2
                    },
                    lineStyle: {
                        color: '#00aaff', // 折线颜色
                        width: 3 // 折线宽度
                    },
                    areaStyle: {
                        // 背景渐变填充
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: 'rgba(0, 170, 255, 0.5)' },
                            { offset: 1, color: 'rgba(0, 170, 255, 0)' }
                        ])
                    }
                }
            ],
            backgroundColor: '#ffffff' // 背景颜色设置为简约白色
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
    <div ref="lineChart" style="width: 100%; height: 125%;"></div>
</template>

<style scoped>
/* 自定义样式，可以根据需要调整 */
</style>
