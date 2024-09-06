<script lang="ts" setup>
import { ref, computed } from 'vue';
import printJS from 'print-js';
import * as XLSX from 'xlsx';
import FrameView from '../FrameView.vue';
import DatePicker from '@/components/DatePicker.vue';

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
const pageSize = ref(15);

// 所有日志数据
const logs = ref([
    // 这里省略已有的日志数据
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
    { province: '北京', age: 35, transaction_amount: 500.00, transaction_time: '2024-09-03 12:30:00', status: '警告', event_type: '金额过大', transaction_type: '在线支付' },
    { province: '上海', age: 28, transaction_amount: 300.00, transaction_time: '2024-09-03 13:00:00', status: '严重', event_type: '异常时间交易', transaction_type: '银行异常时间交易' },
]);

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
                        <span style="white-space: nowrap;">交易日志记录</span>
                        <DatePicker :time="dateRange" @update:internalValue="handleDateChange"></DatePicker>
                        <div class="function-buttons">
                            <el-button style="background-color: #109968; color: white;"
                                @click="exportToExcel">导出为 Excel</el-button>
                            <el-button style="background-color: #DF4023; color: white;"
                                @click="exportToPDF">导出为 PDF</el-button>
                            <el-button style="background-color: #3B64FC; color: white;"
                                @click="clearLogs">清空日志</el-button>
                        </div>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedLogs" border style="width: 100%" id="content">
                            <el-table-column prop="province" label="省份" width="auto"></el-table-column>
                            <el-table-column prop="age" label="年龄" width="auto"></el-table-column>
                            <el-table-column prop="transaction_amount" label="交易金额" width="auto"></el-table-column>
                            <el-table-column prop="transaction_time" label="交易时间" width="200%"></el-table-column>
                            <el-table-column prop="status" label="状态" width="auto"></el-table-column>
                            <el-table-column prop="event_type" label="事件类型" width="auto"></el-table-column>
                            <el-table-column prop="transaction_type" label="交易类型" width="auto"></el-table-column>
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
    /* 确保外层容器不会有滚动条 */
}

.box-card {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
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
