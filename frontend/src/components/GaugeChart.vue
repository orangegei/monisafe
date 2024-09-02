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
                    radius: '99%',
                    center: ['50%', '35%'],
                    axisLine: {
                        lineStyle: {
                            width: 8,
                            color: [
                                [0.3, '#67e0e3'],
                                [0.7, '#37a2da'],
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
                            color: '#fff',
                            width: 2
                        }
                    },
                    splitLine: {
                        distance: -30,
                        length: 30,
                        lineStyle: {
                            color: '#fff',
                            width: 4
                        }
                    },
                    axisLabel: {
                        color: 'inherit',
                        distance: 18,
                        fontSize: 12
                    },
                    detail: {
                        valueAnimation: true,
                        formatter: '{value} ms',
                        color: 'inherit',
                        fontSize: 15,
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
