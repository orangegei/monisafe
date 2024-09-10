<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import FrameView from '../FrameView.vue';
import ChinaPlat from '@/components/ChinaPlat.vue';
import instance from '@/utils/request';

// 定义 Record 接口
interface Record {
    province: string;
    highCount: number;
    timeCount: number;
    atmresponseCount: number;
    forexResponseCount: number;
}

async function fetchData() {
    try {
        const response = await instance.get("/monitor/plat",  {
                headers: {
                    Authorization: sessionStorage.getItem('token')
                }
            });
        if (response.data.code === 0) {
            platData.value = response.data.data as Record[];
            console.log(platData.value);
        }

    } catch (error) {
        console.error('请求失败:', error);
    }
}

onMounted(() => {
    fetchData();
});

// 定义地图数据
const platData = ref<Record[]>([]);

// 切换状态
const showBusiness = ref(true);

// 切换视图的函数
const toggleView = () => {
    showBusiness.value = !showBusiness.value;
};
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <div class="china-map">
                    <ChinaPlat :data="platData" />
                </div>

                <div class="results">
                    <div class="corner-box">
                        <div class="content">
                            <transition name="fade" mode="out-in">
                                <h2 v-if="showBusiness" key="business">To 业务人员</h2>
                                <h2 v-else key="maintain">To 维修人员</h2>
                            </transition>

                            <transition name="slide" mode="out-in">
                                <div class="business" v-if="showBusiness" key="business-content">
                                    <div>大额交易异常次数Top3：</div>
                                    <div class="province-list">上海市，南京市，广州市</div>
                                    <div>异常时间取款笔数Top3：</div>
                                    <div class="province-list">上海市，北京市，杭州市</div>
                                    <div>需重点关注的省份：</div>
                                    <div class="province-list">上海市</div>
                                </div>

                                <div class="maintain" v-else key="maintain-content">
                                    <div>ATM-entry响应时间异常次数Top3：</div>
                                    <div class="province-list">上海市，南京市，广州市</div>
                                    <div>forex-entry响应时间异常次数Top3：</div>
                                    <div class="province-list">上海市，北京市，杭州市</div>
                                    <div>需重点关注的省份：</div>
                                    <div class="province-list">上海市</div>
                                </div>
                            </transition>
                        </div>
                        <div class="corner"></div>

                        <!-- 向右按钮 -->
                        <button class="switch-button" @click="toggleView">→</button>
                    </div>
                </div>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: space-around;
}

.china-map {
    height: 100%;
    width: 60%;
}

.results {
    width: 35%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin-right: 45px;
}

.corner-box {
    position: relative;
    width: 100%;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #ddd;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 15px;
    position: relative;
    background: rgba(255, 255, 255, 0.3);
    /* 设置透明背景 */
    backdrop-filter: blur(10px);
    /* 毛玻璃效果 */
    -webkit-backdrop-filter: blur(10px);
    /* 兼容Safari浏览器 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    /* 添加阴影 */
}

.content {
    z-index: 1;
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}

.business,
.maintain {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    gap: 2vh;
}

.corner {
    position: absolute;
    top: 0;
    right: 0;
    width: 80px;
    height: 80px;
    background-color: #f96d00;
    clip-path: polygon(100% 0, 0 0, 100% 100%);
    z-index: 0;
}

.province-list {
    font-family: 'FZZJ LongYTWJ', sans-serif;
    font-size: 36px;
}

.switch-button {
    position: absolute;
    right: -20px;
    top: 50%;
    transform: translateY(-50%);
    background-color: #f96d00;
    color: white;
    border: none;
    padding: 10px;
    border-radius: 50%;
    cursor: pointer;
    font-size: 18px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.switch-button:hover {
    background-color: #e55b00;
}

/* Fade animation */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter,
.fade-leave-to {
    opacity: 0;
}

/* Slide animation */
.slide-enter-active,
.slide-leave-active {
    transition: all 0.5s ease;
}

.slide-enter,
.slide-leave-to {
    transform: translateX(100%);
    opacity: 0;
}
</style>