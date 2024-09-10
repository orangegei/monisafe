<script setup>
import { onMounted, ref, onBeforeUnmount, watch } from 'vue';
import * as echarts from 'echarts';

const props = defineProps({
    value: {
        type: Number,
        required: true,
    }
});

const gaugeChart = ref(null);

onMounted(() => {
    const chartInstance = echarts.init(gaugeChart.value);

    const setChartOption = () => {
        const option = {
            series: [
                {
                    type: 'gauge',
                    radius: '75%',
                    center: ['40%', '40%'],
                    min: 0,         // 设置最小值
                    max: 200,       // 设置最大值
                    axisLine: {
                        lineStyle: {
                            width: 8,
                            color: [
                                [0.7, '#67e0e3'],
                                [0.9, '#fea443'],
                                [1, '#fd666d']
                            ]
                        }
                    },
                    pointer: {
                        itemStyle: {
                            color: 'auto'
                        }
                    },
                    axisTick: {
                        distance: 30,
                        length: 8,
                        lineStyle: {
                            color: '#4EB9EC',
                            width: 2
                        }
                    },
                    splitLine: {
                        distance: -10,
                        length: 10,
                        lineStyle: {
                            color: '#fff',
                            width: 4
                        }
                    },
                    axisLabel: {
                        color: 'inherit',
                        distance: 18,
                        fontSize: 10
                    },
                    detail: {
                        valueAnimation: true,
                        formatter: '{value} ms',
                        color: 'inherit',
                        fontSize: 18,
                        offsetCenter: [0, '100%']
                    },
                    data: [
                        {
                            value: props.value
                        }
                    ]
                }
            ]
        };
        chartInstance.setOption(option);
    };

    setChartOption();

    watch(() => props.value, () => {
        setChartOption();
    });

    window.addEventListener('resize', chartInstance.resize);

    onBeforeUnmount(() => {
        window.removeEventListener('resize', chartInstance.resize);
        chartInstance.dispose();
    });
});
</script>

<template>
    <div ref="gaugeChart" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 样式可以根据需要调整 */
.gauge-chart {
    width: 100%;
    height: 100%;
}
</style>
