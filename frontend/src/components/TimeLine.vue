<script setup>
import { computed } from 'vue';

// 接收 props
const props = defineProps({
    items: {
        type: Array,
        required: true
    }
});

function isLeft(index) {
    return index % 2 === 0;
}
</script>


<template>
    <div class="time-line">
        <div v-for="(item, index) in items" :key="index" class="time-line-item">
            <div :class="['time-line-content', { 'left': isLeft(index), 'right': !isLeft(index) }]" :style="{ backgroundColor: item.color }" >
                <div class="time-line-content-text">
                    {{ item.text }}
                </div>
            </div>
            <div class="time-line-marker"></div>
        </div>
    </div>
</template>


<style scoped>
.time-line {
    position: relative;
    width: 100%;
    /* padding: 20px 0; */
    /* margin-top: 30%; */
}

.time-line-item {
    position: relative;
    margin-right: 25%;
    margin-bottom: 60%;
}

.time-line-content {
    position: relative;
    width: 150%;
    height: 200%;
    max-width: 130px;
    padding: 20px;
    border-radius: 10px;
    background: #f5f5f5;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    word-wrap: break-word;
    white-space: normal;
    overflow: hidden; 
}


.time-line-content.left {
    left: -140%;
    text-align: left;
}

.time-line-content.right {
    right: -60%;
    text-align: right;
    margin-left: auto;
}

.time-line-content-text {
    font-size: 15px;
}

.time-line-marker {
    position: absolute;
    left: 40%;
    top: 0;
    height: 250%;     /*断线or连线 */
    border-left: 2px solid #6f768be9;
    transform: translateX(-50%);
}
</style>