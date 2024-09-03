<script setup>
import { ref } from 'vue';
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
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>交易日志记录</span>
                        <el-button style="float: right;" @click="clearLogs">清空日志</el-button>
                    </div>
                    <el-table :data="logs" border style="width: 100%">
                        <el-table-column prop="province" label="省份" width="120">
                        </el-table-column>
                        <el-table-column prop="age" label="年龄" width="80">+
                        </el-table-column>
                        <el-table-column prop="transaction_amount" label="交易金额" width="120">
                        </el-table-column>
                        <el-table-column prop="transaction_time" label="交易时间" width="180">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" width="100">
                        </el-table-column>
                         <!-- 大额取款等 -->
                        <el-table-column prop="event_type" label="事件类型" width="120">
                        </el-table-column>
                        <!-- ATM 外汇 -->
                        <el-table-column prop="transaction_type" label="交易类型" width="120">
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

.clearfix{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}
</style>