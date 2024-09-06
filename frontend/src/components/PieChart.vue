<script setup>
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

// 接收父组件传递的 props
const props = defineProps({
    chartData: {
        type: Array,
        required: true,
    },
    title: {
        type: String,
        default: 'Chart'
    },
});

const chart = ref(null);

onMounted(() => {
    const myChart = echarts.init(chart.value);

    const setChartOption = () => {
        const option = {
            title: {
                text: props.title,
                left: 'center',
                textStyle: {
                    color: '#333333', // 标题颜色
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
                    name: 'Access Source',
                    type: 'pie',
                    radius: ['0%', '50%'], // 增大饼图的尺寸
                    center: ['50%', '50%'], // 确保饼图居中显示
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
                    data: props.chartData,
                    itemStyle: {
                        borderRadius: 6, // 让饼图的每个部分边缘圆润
                        borderColor: '#ffffff', // 设置分隔线颜色
                        borderWidth: 1 // 设置分隔线宽度
                    },
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ],
            // backgroundColor: '#f4f4f4' // 设置为浅灰色背景，简约风格
        };
        myChart.setOption(option);
    }

    setChartOption();

    // 监听传入数据的变化并更新图表
    watch(() => props.chartData, setChartOption, { deep: true });

    // 监听窗口尺寸变化，自动调整图表大小
    window.addEventListener('resize', myChart.resize);

    // 组件卸载前销毁 ECharts 实例
    onBeforeUnmount(() => {
        window.removeEventListener('resize', myChart.resize);
        myChart.dispose();
    });
});



</script>

<template>
    <div ref="chart" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 您可以在这里添加自定义样式 */
</style>