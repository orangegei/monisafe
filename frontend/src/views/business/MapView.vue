<script setup>
import FrameView from '../FrameView.vue';
import ChinaMap from '@/components/ChinaMap.vue';
import DatePicker from '@/components/DatePicker.vue';
import SelectIcon from '@/components/SelectIcon.vue';
import SortedTable from '@/components/SortedTable.vue';
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="china-map">
                    <ChinaMap :containerStyle="{ width: '100%', height: '100%' }"></ChinaMap>
                </div>

                <div class="display">
                    <div class="picker">
                        <div class="date-picker-wrapper">
                            <DatePicker></DatePicker>
                        </div>
                        <div class="type-selector">
                            <SelectIcon></SelectIcon>
                        </div>
                    </div>
                    <div class="table-container">
                        <SortedTable></SortedTable>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: space-between;
    height: 100%;
    /* border: 1px solid black; */
}

.china-map {
    height: 100%;
    width: 60%;
    /* border: 1px solid black; */
}

.display {
    width: 35%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 15px;
    /* border: 1px solid black; */
}

.picker {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 20%;
    /* border: 1px solid black; */
}

.display .picker {
    width: 100%;
    height: auto;
}

.display .table-container {
    width: 100%;
    height: 100%;
    overflow: auto;
    /* border: 1px solid black; */
}
</style>