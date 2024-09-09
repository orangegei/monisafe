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
const tableData = ref<Record[]>([]);

// 定义地图数据
const mapData = ref([
    { name: '北京市', value: Math.round(Math.random() * 1000) },
    { name: '天津市', value: Math.round(Math.random() * 1000) },
    { name: '上海市', value: Math.round(Math.random() * 1000) },
    { name: '重庆市', value: Math.round(Math.random() * 1000) },
]);
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="china-map">
                    <ChinaMap :data="mapData" />
                </div>

                <div class="display">
                    <div class="business-area">
                        
                    </div>
                    <div class="maintain-area">

                    </div>
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
    width: 35%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 2%;
}

.business-area{
    background-color: black;
    height: 48%;
    border-radius: 15px;
}

.maintain-area{
    height: 48%;
}
</style>