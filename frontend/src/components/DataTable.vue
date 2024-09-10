<template>
    <el-table :data="props.tableData" :default-sort="{ prop: 'money', order: 'descending' }" style="width: 100%">
        <el-table-column prop="province" label="省份" style="width: 30%;" />
        <el-table-column prop="money" label="总交易金额" sortable style="width: 35%;" />
        <el-table-column prop="amount" label="总交易笔数" sortable :formatter="formatter" style="width: 35%;" />
    </el-table>
</template>

<script lang="ts" setup>
// 接收来自父组件的数据
import type { TableColumnCtx } from 'element-plus'

interface Record {
    province: string
    money: number
    amount: number
}

const props = defineProps<{
    tableData: Record[]
}>();

// 格式化函数
const formatter = (row: Record, column: TableColumnCtx<Record>) => {
    return row.amount;
};
</script>

<style scoped></style>
