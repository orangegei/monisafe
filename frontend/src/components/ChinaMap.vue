<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import chinaJson from '@/assets/中华人民共和国.json';

echarts.registerMap('china', chinaJson);

const chartContainer = ref(null);

const props = defineProps({
  containerStyle: Object,  // 接收父组件传递的样式
});

onMounted(() => {
    const myChart = echarts.init(chartContainer.value);

    const option = {
        // 设置图表的标题，定义了标题的内容、位置和样式
        title: {
            text: '中国地图示例',
            left: 'center'
        },
        // 设置当用户将鼠标悬停在图表的某个区域上时显示的信息框（提示框）
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                return `${params.name}<br/>数据值: ${params.value ? params.value : '无数据'}`;
            }
        },
        // 用于将数据值映射到颜色范围上，通常用于生成热力图或分级颜色地图
        visualMap: {
            min: 0,                          // 数据的最小值
            max: 1000,                       // 数据的最大值
            left: 'left',                    // visualMap 组件的位置，靠左显示
            top: 'bottom',                   // visualMap 组件的位置，靠底部显示
            text: ['高', '低'],              // visualMap 的文本标签，用于解释高值和低值
            calculable: true,                // 是否显示一个可以拖动的手柄来动态调整 visualMap
            inRange: {
                color: ['#50a3ba', '#eac736', '#d94e5d'] // 定义颜色范围，从低到高依次为蓝、黄、红
            }
        },
        series: [
            {
                name: '随机数据',             // 系列的名称
                type: 'map',                 // 系列的类型（地图类型）
                map: 'china',                // 要使用的地图类型（中国地图）
                roam: true,                  // 地图是否可以缩放和平移
                // 定义地图区域的样式
                itemStyle: {
                    // 定义地图区域在正常状态下的样式
                    normal: {
                        areaColor: '#cccccc',
                        borderColor: '#111'
                    },
                    // 定义了地图区域在鼠标悬停状态下的样式
                    emphasis: {
                        areaColor: '#ffcc00'
                    }
                },
                // 定义每个地图区域的数据值
                data: [
                    { name: '北京市', value: Math.round(Math.random() * 1000) },
                    { name: '天津市', value: Math.round(Math.random() * 1000) },
                    { name: '上海市', value: Math.round(Math.random() * 1000) },
                    { name: '重庆市', value: Math.round(Math.random() * 1000) },
                ]
            }
        ]
    };

    myChart.setOption(option);

    // 监听窗口大小变化，自动调整图表大小
    window.addEventListener('resize', () => {
        myChart.resize();
    });
});
</script>

<template>
    <div ref="chartContainer" :style="containerStyle"></div>
</template>

<style scoped>
/* 样式定义可以根据需求调整 */
</style>