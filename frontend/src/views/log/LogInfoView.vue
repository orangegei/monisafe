<script setup>
import { ref } from 'vue';
import printJS from 'print-js';
import * as XLSX from 'xlsx';
import FrameView from '../FrameView.vue';

const logs = ref([
    {
        province: '北京',
        age: 35,
        transaction_amount: 500.00,
        transaction_time: '2024-09-03 12:30:00',
        status: '成功',
        event_type: '购买',
        transaction_type: '在线支付'
    },
    {
        province: '上海',
        age: 28,
        transaction_amount: 300.00,
        transaction_time: '2024-09-03 13:00:00',
        status: '失败',
        event_type: '转账',
        transaction_type: '银行转账'
    },
    // 可以添加更多日志条目...
]);

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
        printable: 'content',  // 指定要打印的区域，即表格的容器ID
        type: 'html',
        targetStyles: ['*'],  // 确保打印时保留所有样式
        style: `
          @media print {
              table {
                  width: 100% !important;
                  table-layout: auto !important;  /* 自动调整列宽 */
              }
              th, td {
                  word-wrap: break-word !important;  /* 确保长单词换行 */
                  white-space: normal !important;    /* 确保文字不会溢出 */
                  padding: 8px;                      /* 单元格内边距 */
                  text-align: center !important;     /* 将表格内容居中对齐 */
                  vertical-align: middle !important; /* 垂直居中对齐 */
              }
              th {
                  font-weight: bold;  /* 表头加粗 */
              }
          }
        `,
        documentTitle: '交易日志记录',
        header: '<h2 style="text-align: center;">交易日志记录</h2>',  // 自定义标题
        scanStyles: false  // 仅使用指定的样式
    });
};

</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>交易日志记录</span>
                        <div>
                            <!-- 导出为 Excel 按钮：绿底白字 -->
                            <el-button style="margin-right: 10px; background-color: #109968; color: white;"
                                @click="exportToExcel">导出为 Excel</el-button>

                            <!-- 导出为 PDF 按钮：红底白字 -->
                            <el-button style="margin-right: 10px; background-color: #DF4023; color: white;"
                                @click="exportToPDF">导出为 PDF</el-button>

                            <!-- 清空日志 按钮：蓝底白字 -->
                            <el-button style="background-color: #3B64FC; color: white;" @click="clearLogs">清空日志</el-button>
                        </div>
                    </div>

                    <el-table :data="logs" border style="width: 100%" id="content">
                        <el-table-column prop="province" label="省份" width="auto">
                        </el-table-column>
                        <el-table-column prop="age" label="年龄" width="auto">
                        </el-table-column>
                        <el-table-column prop="transaction_amount" label="交易金额" width="auto">
                        </el-table-column>
                        <el-table-column prop="transaction_time" label="交易时间" width="auto">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" width="auto">
                        </el-table-column>
                        <el-table-column prop="event_type" label="事件类型" width="auto">
                        </el-table-column>
                        <el-table-column prop="transaction_type" label="交易类型" width="auto">
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 100%;
}

.box-card {
    width: 100%;
    height: 100%;
    overflow: auto;
}

.clearfix {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.el-table {
    width: 100% !important;
    /* 让表格填满整个页面 */
    table-layout: fixed !important;
    /* 确保表格根据列宽自动调整 */
}

.el-table-column {
    word-wrap: break-word !important;
    /* 确保长单词会换行 */
    white-space: normal !important;
    /* 确保内容可以换行 */
}
</style>
