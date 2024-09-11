<script lang="ts" setup>
import { ref, computed, onMounted, watch } from 'vue';
import * as XLSX from 'xlsx';
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';
import instance from '@/utils/request';

// 日期范围
const today = new Date();
const lastmonth = new Date(today);
lastmonth.setDate(today.getDate() - 30);
const dateRange = ref<[Date, Date]>([
    lastmonth,
    today,
]);

function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

const formatDate = (date: Date): string => {
    return date.toISOString().split('T')[0];
};

// 分页相关数据
const currentPage = ref(1);
const pageSize = ref(15);

// 所有日志数据
const logs = ref([]);

const fetchLogs = async () => {
    try {
        const response = await instance.get('/monitor/detailedlogs', {
            params: {
                startTime: formatDate(dateRange.value[0]),
                endTime: formatDate(dateRange.value[1]),
            },
            headers: {
                Authorization: sessionStorage.getItem('token'),
            },
        });

        if (response.data.code === 0) {
            // 获取并更新日志数据
            const data = response.data.data;
            logs.value = data.map((log: any) => ({
                serial_number: log.serialNumber,
                province: log.province,
                age: log.age || '无',
                transaction_amount: log.transactionAmount || '无',
                transaction_time: log.transactionTime,
                status: log.status,
                event_type: log.eventType,
            }));
            console.log(logs);
        }
    } catch (error) {
        console.error('Failed to fetch logs data', error);
    }
};

// 在组件挂载时调用 fetchLogs 来获取日志数据
onMounted(() => {
    fetchLogs();
});

// 计算当前页显示的数据
const paginatedLogs = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return logs.value.slice(start, end);
});


// 点击确认按钮时的处理方法
async function handleConfirmClick() {
    await fetchLogs();
}

const clearLogs = () => {
    logs.value = [];
};

const exportToExcel = () => {
    const worksheet = XLSX.utils.json_to_sheet(logs.value);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Logs');
    XLSX.writeFile(workbook, 'logs.xlsx');
};

// const filteredEventTypes = ref([]); // 用于存储当前筛选的事件类型

// const filterEventType = (value: string, row: any) => {
//     return row.event_type === value;
// };
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <di style="white-space: nowrap;">告警日志记录</di>
                        <div class="picker">
                            <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                            <ElButton type="primary" class="confirm-button" @click="handleConfirmClick">确认</ElButton>
                        </div>
                        <div class="function-buttons">
                            <el-button style="background-color: #109968; color: white;" @click="exportToExcel">导出为
                                Excel</el-button>
                            <el-button style="background-color: #3B64FC; color: white;"
                                @click="clearLogs">清空日志</el-button>
                        </div>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedLogs" border style="width: 100%;" id="content">
                            <el-table-column prop="serial_number" label="流水号" width="auto"
                                align="center"></el-table-column>
                            <el-table-column prop="province" label="省份" width="100px" align="center"></el-table-column>
                            <el-table-column prop="age" label="年龄" width="100px" align="center"></el-table-column>
                            <el-table-column prop="transaction_time" label="交易时间" width="auto"
                                align="center"></el-table-column>
                            <el-table-column prop="transaction_amount" label="交易金额" width="100px"
                                align="center"></el-table-column>
                            <el-table-column prop="event_type" label="告警内容" width="auto"
                                align="center"></el-table-column>
                            <el-table-column prop="status" label="状态" width="100px" align="center"></el-table-column>
                        </el-table>
                    </div>

                    <el-pagination v-model:current-page="currentPage" :page-size="pageSize" :total="logs.length"
                        layout="prev, pager, next" style="margin-top: 20px; text-align: right;">
                    </el-pagination>
                </el-card>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.box-card {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    position: relative;
    background: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.clearfix {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1%;
}

.picker {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    gap: 1vw;
}

.function-buttons {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 10px;
}
</style>
