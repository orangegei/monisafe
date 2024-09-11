<script setup>
import { onMounted, ref, watch, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';
import chinaJson from '@/assets/中华人民共和国.json';

echarts.registerMap('china', chinaJson);

const chartContainer = ref(null);

// 接收父组件传递的 props
const props = defineProps({
    data: Array,              // 数据
    visualMapConfig: Object,  // 最大值和最小值
});

onMounted(() => {
    const myChart = echarts.init(chartContainer.value);

    const setChartOption = () => {
        const option = {
            title: {
                text: '中国地图示例',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    const data = params.data;
                    if (data) {
                        return `${params.name}<br/>
                                总交易金额: ${data.value1}<br/>
                                总交易笔数: ${data.value2}<br/>`;
                    } else {
                        // console.log(params.name);
                        return `${params.name}<br/>无数据`;
                    }

                    // return `${params.name}<br/>数据值: ${params.value ? params.value : '无数据'}`;
                }
            },
            visualMap: {
                min: props.visualMapConfig.min,
                max: props.visualMapConfig.max,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],
                calculable: true,
                inRange: {
                    color: ['#50a3ba', '#eac736', '#d94e5d']
                }
            },
            series: [
                {
                    name: '随机数据',
                    type: 'map',
                    map: 'china',
                    roam: true,
                    itemStyle: {
                        normal: {
                            areaColor: '#cccccc',
                            borderColor: '#111'
                        },
                        emphasis: {
                            areaColor: '#ffcc00'
                        }
                    },
                    // data: props.data // 使用传递的数据
                    data: props.data.map(item => ({
                        name: item.name,
                        value: item.value1,  // 使用 value1 来映射颜色
                        value1: item.value1,
                        value2: item.value2,
                    }))
                }
            ]
        };
        myChart.setOption(option);
    };

    setChartOption();

    // 监听窗口大小变化，自动调整图表大小
    window.addEventListener('resize', () => {
        myChart.resize();
    });

    // 监听传入数据的变化并更新图表
    watch(() => props.data, setChartOption, { deep: true });

    // 监听窗口尺寸变化，自动调整图表大小
    // window.addEventListener('resize', myChart.resize);

    // 组件卸载前销毁 ECharts 实例
    onBeforeUnmount(() => {
        window.removeEventListener('resize', myChart.resize);
        myChart.dispose();
    });
});
</script>

<template>
    <div ref="chartContainer" style="width: 100%; height: 100%;"></div>
</template>

<style scoped>
/* 样式定义可以根据需求调整 */
</style>
