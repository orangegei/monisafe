<script setup>
import SettingsFrameView from './SettingsFrameView.vue';
import { ref } from 'vue';
import { ElTable, ElTableColumn } from 'element-plus';

const securityRecords = ref([
    {
        action: '账户登录',
        ip: 'IP:117.162.85.14',
        time: '2024-07-18 22:58',
    },
    {
        action: '账户登录',
        ip: 'IP:153.3.60.154',
        time: '2024-07-07 09:37',
    },
    {
        action: '绑定了手机号码',
        ip: 'IP:153.3.60.162',
        time: '2024-04-28 23:09',
    },
]);
</script>

<template>
    <SettingsFrameView>
        <template #main-body>
            <div class="container">
                <h1>安全日志</h1>
                <div>
                    <el-table :data="securityRecords" style="width: 70%">
                        <el-table-column prop="action" label="操作详情" width="280"></el-table-column>
                        <el-table-column prop="ip" label="IP地址" width="280"></el-table-column>
                        <el-table-column prop="time" label="时间"></el-table-column>
                    </el-table>
                </div>
            </div>
        </template>
    </SettingsFrameView>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin: 30px 50px;
}
</style>