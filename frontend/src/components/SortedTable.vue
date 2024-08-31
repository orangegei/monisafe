<script lang="ts" setup>
import type { TableColumnCtx } from 'element-plus'

interface Record {
    city: string
    money: number
    amount: number
}

const formatter = (row: Record, column: TableColumnCtx<Record>) => {
    return row.amount;
}

const tableData: Record[] = [
    // 此处与后端交互数据
    {
        city: '北京',
        money: 1000,
        amount: 100
    },
    {
        city: '上海',
        money: 2000,
        amount: 200
    },
    {
        city: '广州',
        money: 3000,
        amount: 300
    },
    {
        city: '深圳',
        money: 4000,
        amount: 400
    },
    {
        city: '杭州',
        money: 5000,
        amount: 500
    },
    {
        city: '北京',
        money: 1000,
        amount: 100
    },
    {
        city: '上海',
        money: 2000,
        amount: 200
    },
    {
        city: '广州',
        money: 3000,
        amount: 300
    },
    {
        city: '深圳',
        money: 4000,
        amount: 400
    },
    {
        city: '杭州',
        money: 5000,
        amount: 500
    },
    {
        city: '北京',
        money: 1000,
        amount: 100
    },
    {
        city: '上海',
        money: 2000,
        amount: 200
    },
    {
        city: '广州',
        money: 3000,
        amount: 300
    },
    {
        city: '深圳',
        money: 4000,
        amount: 400
    },
    {
        city: '杭州',
        money: 5000,
        amount: 500
    },
    {
        city: '北京',
        money: 1000,
        amount: 100
    },
    {
        city: '上海',
        money: 2000,
        amount: 200
    },
    {
        city: '广州',
        money: 3000,
        amount: 300
    },
    {
        city: '深圳',
        money: 4000,
        amount: 400
    },
    {
        city: '杭州',
        money: 5000,
        amount: 500
    },
]
</script>

<template>
    <el-table :data="tableData" :default-sort="{ prop: 'money', order: 'descending' }" style="width: 100%">
        <el-table-column prop="city" label="城市" style="width: 30%;" />
        <el-table-column prop="money" label="总交易金额" sortable style="width: 35%;" />
        <el-table-column prop="amount" label="总交易笔数" sortable :formatter="formatter" style="width: 35%;" />
    </el-table>
</template>
