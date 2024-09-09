<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue';
import printJS from 'print-js';
import * as XLSX from 'xlsx';
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';
import instance from '@/utils/request';

// 日期范围
const dateRange = ref<[Date, Date]>([
    new Date(2024, 8, 1),
    new Date(2024, 8, 2),
]);

function handleDateChange(newRange: [Date, Date]) {
    dateRange.value = newRange;
}

// 分页相关数据
const currentPage = ref(1);
const pageSize = ref(10);

// 所有日志数据
const logs = ref([]);
// const logs = ref([
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
//     { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
//     { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
// ]);


const fetchLogs = async () => {
    try {
        const response = await instance.get('/monitor/logs', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            },
            params: {
                startDate: dateRange.value[0].toISOString(),
                endDate: dateRange.value[1].toISOString(),
            }
        });

        if (response.data.code === 0) {
            // 获取并更新日志数据
            const data = response.data.data;
            logs.value = data.map((log: any) => ({
                serial_number: log.serialNumber, 
                province: log.province,
                age: log.age,
                transaction_amount: log.transactionAmount,
                transaction_time: log.transactionTime,
                status: log.status,
                event_type: log.eventType,
                transaction_type: log.transactionType
            }));
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

const clearLogs = () => {
    logs.value = [];
};

const exportToExcel = () => {
    const worksheet = XLSX.utils.json_to_sheet(logs.value);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Logs');
    XLSX.writeFile(workbook, 'logs.xlsx');
};

const exportToPDF = () => {
    printJS({
        printable: 'content',
        type: 'html',
        targetStyles: ['*'],
        style: `
          @media print {
              table {
                  width: 100% !important;
                  table-layout: auto !important;
              }
              th, td {
                  word-wrap: break-word !important;
                  white-space: normal !important;
                  padding: 8px;
                  text-align: center !important;
                  vertical-align: middle !important;
              }
              th {
                  font-weight: bold;
              }
          }
        `,
        documentTitle: '交易日志记录',
        header: '<h2 style="text-align: center;">交易日志记录</h2>',
        scanStyles: false
    });
};
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="white-space: nowrap;">告警日志记录</span>
                        <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                        <div class="function-buttons">
                            <el-button style="background-color: #109968; color: white;" @click="exportToExcel">导出为
                                Excel</el-button>
                            <el-button style="background-color: #DF4023; color: white;" @click="exportToPDF">导出为
                                PDF</el-button>
                            <el-button style="background-color: #3B64FC; color: white;"
                                @click="clearLogs">清空日志</el-button>
                        </div>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedLogs" border style="width: 100%;" id="content">
                            <el-table-column prop="serial_number" label="流水号" width="auto"></el-table-column>
                            <el-table-column prop="province" label="省份" width="100px"></el-table-column>
                            <el-table-column prop="age" label="年龄" width="100px"></el-table-column>
                            <el-table-column prop="transaction_time" label="交易时间" width="auto"></el-table-column>
                            <el-table-column prop="transaction_amount" label="交易金额" width="100px"></el-table-column>
                            <el-table-column prop="event_type" label="告警内容" width="auto"></el-table-column>
                            <el-table-column prop="status" label="状态" width="100px"></el-table-column>
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
    /* 设置透明背景 */
    backdrop-filter: blur(10px);
    /* 毛玻璃效果 */
    -webkit-backdrop-filter: blur(10px);
    /* 兼容Safari浏览器 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.clearfix {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1%;
}

.function-buttons {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 10px;
}
</style>
