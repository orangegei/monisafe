<script lang="ts" setup>
import { ref } from 'vue'
import axios from 'axios';
import instance from '@/utils/request'
import { ElMessage } from 'element-plus'
import FrameView from '../FrameView.vue';
import ChinaMap from '@/components/ChinaMap.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import DataTable from '@/components/DataTable.vue';

const today = new Date();
const yesterday = new Date(today);
yesterday.setDate(today.getDate() - 1);

// 日期范围（默认展示昨天一天的数据）
const dateRange = ref<[Date, Date]>([
    yesterday,
    today,
]);

// 将日期转换为字符串
const formatDate = (date: Date): string => {
    return date.toISOString().split('T')[0]; // 只保留日期部分
};


// 业务类型（ATM或外汇）
const businessType = ref<string | null>(null);

// 当用户在日期选择器中选择新的日期范围时，该函数将被调用它的作用是更新应用内部状态（dateRange.value）以反映用户的新选择
function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

function handleBusinessTypeChange(type: string) {
    businessType.value = type;
}

// 定义表格数据
const tableData = ref<Record[]>([]);
const mapData = ref([]);


// 定义地图数据
// const mapData = ref([
//     { name: '北京市', value: Math.round(Math.random() * 1000) },
//     { name: '天津市', value: Math.round(Math.random() * 1000) },
//     { name: '上海市', value: Math.round(Math.random() * 1000) },
//     { name: '重庆市', value: Math.round(Math.random() * 1000) },
// ]);

// function updateMapData(data: any) {
//     // 根据返回的数据更新地图显示
//     mapData.value = data.map((item: any) => ({
//         name: item.city,
//         value: item.money,
//     }));
// }

// function updateTableData(data: any) {
//     // 根据返回的数据更新表格显示
//     tableData.value = data.map((item: any) => ({
//         city: item.city,
//         money: item.money,
//         amount: item.amount,
//     }));
// }


// 定义 Record 接口
interface Record {
    city: string
    money: number
    amount: number
}

// 验证日期和业务类型是否已选择
function validateSelections() {
    if (!dateRange.value || !businessType.value) {
        ElMessage.error('日期或业务内容不能为空');
        return false;
    }
    return true;
}

// 点击确认按钮时的处理方法
async function handleConfirmClick() {
    if (!validateSelections()) {
        return;
    }
    const [startDate, endDate] = dateRange.value;
    // 根据业务类型发送不同的请求
    if (businessType.value === 'atm') {
        await fetchData('/business/atm/map', startDate, endDate);
    } else if (businessType.value === 'forex') {
        await fetchData('/business/forex/map', startDate, endDate);
    }
}

async function fetchData(url: string, startDate: Date, endDate: Date) {
    try {
        const params = {
            startTime: formatDate(startDate),
            endTime: formatDate(endDate),
        };
        const response = await instance.get(url, {
            params,
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if(response.data.code===0) {
            const { province, count, amount } = response.data.data;
            
            // 更新表格和地图数据
            tableData.value = province.map((name: string, index: number) => ({
                city: name,
                amount: count[index],
                money: amount[index],
            }));

            mapData.value = province.map((name: string, index: number) => ({
                name:name,
                value1:amount[index],
                value2:count[index],
            }));
            // console.log(mapData);
        }

    } catch (error) {
        console.error('请求失败:', error);
    }
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
                    <div class="picker">
                        <div class="date-picker-wrapper">
                            <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                        </div>
                        <div class="type-selector">
                            <!-- SelectIcon 组件更新业务类型 -->
                            <SelectIcon @change="handleBusinessTypeChange" />
                        </div>
                        <div>
                            <ElButton type="primary" class="confirm-button" @click="handleConfirmClick">确认</ElButton>
                        </div>
                    </div>
                    <div class="table-container">
                        <DataTable :tableData="tableData" />
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
    width: 40%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 2%;
}

.picker {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 20%;
}

.confirm-button {
    border-radius: 10px;
}

.display .picker {
    width: 100%;
    height: auto;
}

.display .table-container {
    width: 100%;
    height: 100%;
    overflow: auto;
}
</style>
