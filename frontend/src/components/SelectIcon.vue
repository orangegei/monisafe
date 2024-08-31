<script lang="ts" setup>
import { ref } from 'vue'

const value = ref('')
const options = [
    {
        value: 'atm',
        label: 'ATM',
    },
    {
        value: 'forex',
        label: '外汇',
    },
]
</script>

<template>
    <el-select v-model="value" clearable placeholder="Select" style="width: 100px">
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
</template>

<style scoped>
</style>