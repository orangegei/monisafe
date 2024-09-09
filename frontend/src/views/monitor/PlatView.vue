<script lang="ts" setup>
import { ref } from 'vue'
import FrameView from '../FrameView.vue';
import ChinaMap from '@/components/ChinaMap.vue';

// 定义 Record 接口
interface Record {
    city: string
    money: number
    amount: number
}

// 定义表格数据
const tableData = ref < Record[] > ([]);

// 定义地图数据
const mapData = ref([
    { name: '北京市', value: Math.round(Math.random() * 1000) },
    { name: '天津市', value: Math.round(Math.random() * 1000) },
    { name: '上海市', value: Math.round(Math.random() * 1000) },
    { name: '重庆市', value: Math.round(Math.random() * 1000) },
]);

function updateMapData(data: any) {
    // 根据返回的数据更新地图显示
    mapData.value = data.map((item: any) => ({
        name: item.city,
        value: item.money,
    }));
}

function updateTableData(data: any) {
    // 根据返回的数据更新表格显示
    tableData.value = data.map((item: any) => ({
        city: item.city,
        money: item.money,
        amount: item.amount,
    }));
}
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="china-map">
                    <ChinaMap :data="mapData" />
                </div>

                <div class="display">

                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: space-between;
    height: 100%;
}

.china-map {
    height: 100%;
    width: 60%;
}

.display {
    width: 40%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 2%;
}
</style>