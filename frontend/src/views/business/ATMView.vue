<script lang="ts" setup>
import { ref } from 'vue';
import BusinessFrameView from './BusinessFrameView.vue';

const atmClassName = ref('atm-active');
const forexClassName = ref('forex');
</script>

<template>
    <BusinessFrameView :atm-class="atmClassName" :forex-class="forexClassName" businessbackgroundColor="#FFD700">
        <template #main-body-display>
            <!-- 在此编辑 ATM 页面主体内容 -->
            <h1>Hello World!</h1>
        </template>
    </BusinessFrameView>
</template>

<style scoped></style>