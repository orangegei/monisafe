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
                left: 'center',
                textStyle: {
                    color: '#333333',
                    fontSize: 16,
                    fontWeight: 'bold'
                }
            },
            tooltip: {
                trigger: 'item',
                backgroundColor: 'rgba(50, 50, 50, 0.7)', // 半透明背景
                borderWidth: 0,
                textStyle: {
                    color: '#ffffff'
                }
            },
            legend: {
                show: false, // 隐藏图例
            },
            series: [
                {
                    name: '交易笔数',
                    type: 'pie',
                    radius: ['35%', '65%'], // 调整内外半径使其更占空间
                    center: ['50%', '50%'], // 确保饼图居中
                    avoidLabelOverlap: false,
                    label: {
                    show: true, // 显示标签
                    position: 'outside', // 标签位置在色块外部
                    formatter: '{d}%', // 标签只显示百分比
                    fontSize: 14,
                    color: '#333333', // 标签文字颜色为黑色，匹配简约风格
                    fontWeight: 'bold'
                },
                labelLine: {
                    show: true, // 显示引线
                    length: 10, // 引线第一段长度
                    length2: 10, // 引线第二段长度
                    smooth: true, // 引线圆滑处理
                    lineStyle: {
                        color: '#aaaaaa', // 引线颜色
                        width: 2
                    }
                },
                    data: props.chartData.map((item, index) => ({
                        ...item,
                        itemStyle: {
                            color: ['#FF6F61', '#6B5B95', '#88B04B', '#F7CAC9', '#92A8D1', '#FFECA1'][index % 6], // 使用简约风格的配色
                            borderRadius: 6,
                            borderColor: '#ffffff',
                            borderWidth: 1
                        }
                    })),
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '16',
                            fontWeight: 'bold'
                        },
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
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
