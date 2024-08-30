<script setup>
import { defineProps } from 'vue';

// const props = defineProps({
//     monitorbackgroundColor: {
//         type: String,
//         default: '#f9fbfd' // 默认背景颜色
//     },
//     businessbackgroundColor: {
//         type: String,
//         default: '#f9fbfd' // 默认背景颜色
//     }
// });
</script>

<template>
    <div class="common-layout">
        <el-container style="height: 100%;">
            <!-- Aside -->
            <el-aside width="250px" class="monisafe-sidebar">
                <div class="logo-item-container">
                    <img src="../assets/logo-MoniSafe-透明.png" alt="MoniSafe Logo" class="logo">

                    <div class="menu-items">
                        <div class="menu-title">
                            Main Menu
                        </div>

                        <!-- <RouterLink to="/monitor" class="item"
                            :style="{ backgroundColor: monitorbackgroundColor }" style="text-decoration: none;">
                            <img src="../assets/monitor-监控.svg" alt="monitor" class="menu-icon">
                            <span class="text">监控</span>
                        </RouterLink>

                        <RouterLink to="/business/atm" class="item"
                            :style="{ backgroundColor: businessbackgroundColor }" style="text-decoration: none;">
                            <img src="../assets/business-业务.svg" alt="business" class="menu-icon">
                            <span class="text">业务</span>
                        </RouterLink> -->

                        <RouterLink to="/monitor" class="item" style="text-decoration: none;">
                            <img src="../assets/monitor-监控.svg" alt="monitor" class="menu-icon">
                            <span class="text">监控</span>
                        </RouterLink>

                        <RouterLink to="/business/atm" class="item" style="text-decoration: none;">
                            <img src="../assets/business-业务.svg" alt="business" class="menu-icon">
                            <span class="text">业务</span>
                        </RouterLink>

                        <RouterLink to="/settings" class="item" style="text-decoration: none;">
                            <img src="../assets/settings-设置.svg" alt="settings" class="menu-icon">
                            <span class="text">设置</span>
                        </RouterLink>
                    </div>
                </div>
                <div class="footer">
                    @MoniSafe.2024<br>
                    Bank monitor platform is a solution for finance
                </div>
            </el-aside>

            <el-container>
                <!-- header -->
                <el-header class="monisafe-header">
                    <div class="header-content">
                        <div class="welcome-message">
                            <h1>Welcome back, Jack!</h1>
                        </div>

                        <div class="atm-forex-btn">
                            <slot name="atm-forex"></slot>
                        </div>

                        <div class="header-icons">
                            <div class="icon-button">
                                <img src="@/assets/moon-夜间模式.svg" alt="Moon Icon" class="button-icon">
                            </div>
                            <div class="icon-button">
                                <img src="@/assets/ring-日志消息.svg" alt="Ring Icon" class="button-icon">
                            </div>
                            <!-- <div class="profile">
                                <img src="@/assets/settings-设置.svg" alt="Profile Icon" class="picture">
                            </div> -->

                            <div class="picture" style="padding:0 5px;cursor: pointer;">
                                <img src="../assets/profile-头像.jpeg" alt="Profile Icon"
                                    style="width: 50px;height: 50px;border-radius: 50%;">
                            </div>
                        </div>


                    </div>
                </el-header>

                <!-- main -->
                <el-main class="monisafe-main">
                    <slot name="main-body"></slot>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<style scoped>
html,
body {
    margin: 0;
    padding: 0;
    overflow: hidden;
    height: 100%;
}

.common-layout {
    height: 98vh;
    overflow: hidden;
    border-radius: 10px;
    /* background: linear-gradient(to bottom, #BEDCFE, #ffffff); */
    /* 添加轻微的径向渐变效果来模拟柔和的光晕 */
    /* background: radial-gradient(circle at top, #BEDCFE, #fff 50%);
    background: radial-gradient(circle at top left, #DBE9FD, #fff 50%); */
    /* linear-gradient(to top, #BEDCFE, #ffffff); */
    /* background-color: #f9fbfd; */
    /* background: linear-gradient(to bottom, #D1E3FF 0%, #f0f4fa 25%, #ffffff 50%, #E0E6F2 75%, #D1E3FF 100%); */
    /* background: linear-gradient(to right, #e9f1f6 0%, #ecf5fc 50%, #e9f1f6 100%); */

    /* background-color: #F5F6F8; */
}

.monisafe-sidebar {
    /* background-color: #f9fbfd; */
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 0;
}

.logo-item-container {
    text-align: center;
}

.logo {
    width: 150px;
    height: auto;
}

/* Menu */
.menu-items {
    display: flex;
    flex-direction: column;
    margin: 20px 10px 10px 10px;
    border-radius: 8px;
}

.menu-title {
    color: #c0c4cc;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 20px;
    margin-bottom: 10px;
    text-align: center;
}

.menu-items .item {
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    padding: 10px;
    margin: 10px;
}

.item .menu-icon {
    width: 28px;
    height: 28px;
    margin-right: 10px;
    color: black;
}

.item .text {
    font-size: 20px;
    color: black;
}

.item:hover {
    background-color: #E2E6F9;
    border-radius: 8px;
}

.footer {
    text-align: center;
    color: #909399;
    font-size: 12px;
    border-top: 1px solid #e4e7ed;
    padding-top: 20px;
}

/* header */
.monisafe-header {
    /* background: linear-gradient(to right, #EDF0F5 0%, #f0f4fa 50%, #E0E6F2 75%, #e7eefc 100%); */
    /* background-color: #e9f1f6; */
    /* background: linear-gradient(to right, #e9f1f6 0%, #ecf5fc 50%, #e9f1f6 100%); */
    border-bottom: 1px solid #c0b9b9;
    padding: 10px 20px;
    height: auto;
    border-top-left-radius: 20px;
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

/* welcome-message */
.welcome-message {
    font-size: 20px;
    font-weight: bold;
}

.welcome-message h1 {
    margin: 10px;
    font-size: 24px;
    font-weight: bold;
}

.welcome-message p {
    margin: 10px;
    font-size: 18px;
    color: #c0c4cc;
}

/* header-icons */
.header-icons {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
}

.icon-button {
    width: 45px;
    height: 45px;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(255, 255, 255);
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.icon-button:hover {
    background-color: #e0e0e0;
}

.button-icon {
    width: 30px;
    height: 30px;
}

/* .profile {
    width: 50px;
    height: 50px;
    border: 2px solid #2c3e50;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f9fbfd;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 10px;
} */

/* .picture {
    width: 32px;
    height: 32px;
} */

/* main */
.monisafe-main {
    /* background: linear-gradient(to right, #EDF0F5 0%, #f0f4fa 50%, #E0E6F2 75%, #e7eefc 100%); */
    /* background-color: #e9f1f6; */
    /* background: linear-gradient(to right, #e9f1f6 0%, #ecf5fc 50%, #e9f1f6 100%); */
    height: calc(100% - 64px);
    padding: 20px;
    border-bottom-left-radius: 20px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}
</style>
