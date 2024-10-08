<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import instance from '@/utils/request'

// 控制侧边栏显示状态的变量
const isSidebarCollapsed = ref(false);

// 控制监控子菜单的显示状态
const isMonitorHovered = ref(false);

// 控制业务子菜单的显示状态
const isBusinessHovered = ref(false);

// 切换侧边栏的显示状态
const toggleSidebar = () => {
    isSidebarCollapsed.value = !isSidebarCollapsed.value;
};

// 定义定时器
let hoverTimer = null;

// 鼠标悬浮在业务菜单项上时触发，显示弹窗
const showBusinessSubMenu = () => {
    clearTimeout(hoverTimer);  // 清除之前的定时器
    hoverTimer = setTimeout(() => {
        isBusinessHovered.value = true;
    }, 300);  // 300ms 延迟显示弹窗
};

// 鼠标移出业务菜单项或子菜单时触发，隐藏弹窗
const hideBusinessSubMenu = () => {
    clearTimeout(hoverTimer);  // 清除之前的定时器
    hoverTimer = setTimeout(() => {
        isBusinessHovered.value = false;
    }, 100);  // 100ms 延迟隐藏弹窗
};

// 鼠标悬浮在监控菜单项上时触发，显示弹窗
const showMonitorSubMenu = () => {
    clearTimeout(hoverTimer);  // 清除之前的定时器
    hoverTimer = setTimeout(() => {
        isMonitorHovered.value = true;
    }, 300);  // 300ms 延迟显示弹窗
};

// 鼠标移出监控菜单项或子菜单时触发，隐藏弹窗
const hideMonitorSubMenu = () => {
    clearTimeout(hoverTimer);  // 清除之前的定时器
    hoverTimer = setTimeout(() => {
        isMonitorHovered.value = false;
    }, 100);  // 100ms 延迟隐藏弹窗
};

const exit = async () => {
    try {
        const response = await instance.post('/user/exit', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code === 0) {
            // 退出成功，清除会话中的用户信息
            sessionStorage.removeItem('token');
        } else {
            console.error('Failed to logout:', response.data.message);
        }
    } catch (error) {
        console.error('Error during logout:', error);
    }
};

// 选择的状态
const selectedStatus = ref('');

const updateStatus = async (status) => {
    try {
        console.log(status);
        const response = await instance.post('/user/updateStatus', { "status": status }, {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code !== 0) {
            throw new Error(response.data.message);
        }
    } catch (error) {
        throw error;
    }
};

// 处理命令
const handleCommand = async (command) => {
    selectedStatus.value = command;
    try {
        // 发送状态到后端
        await updateStatus(selectedStatus.value);
        ElMessage({
            message: `修改成功，当前状态为 ${selectedStatus.value === '空闲' ? '空闲' : '忙碌'}`,
            type: 'success',
        });
    } catch (error) {
        ElMessage({
            message: '修改状态失败，请重试',
            type: 'error',
        });
        console.error('Error updating status:', error);
    }
};

// 当前用户的用户名
const currentUsername = ref('');

// 在组件挂载时获取当前用户的用户名
onMounted(async () => {
    try {
        const response = await instance.get('/user/getUserName' , {
            headers: {
                Authorization: sessionStorage.getItem('token')
            }
        });
        if (response.data.code === 0) {
            currentUsername.value = response.data.data;
        } else {
            throw new Error(response.data.message);
        }
    } catch (error) {
        ElMessage({
            message: '获取用户信息失败，请重试',
            type: 'error',
        });
        console.error('Error fetching current user:', error);
    }
});

</script>

<template>
    <div class="common-layout">
        <el-container style="height: 100%; display: flex; position: relative;">
            <!-- Aside -->
            <el-aside :width="isSidebarCollapsed ? '80px' : '250px'" class="monisafe-sidebar">
                <div class="business-content">
                    <div class="logo-item-container" :style="{ textAlign: isSidebarCollapsed ? 'center' : 'left' }">
                        <img src="../assets/logo-MoniSafe-透明.png" alt="MoniSafe Logo" class="logo"
                            :style="{ width: isSidebarCollapsed ? '80px' : '150px' }">
                    </div>

                    <div class="menu-items">
                        <div class="menu-title" v-show="!isSidebarCollapsed">
                            Main Menu
                        </div>

                        <!-- Monitor Menu Item with Hover Submenu -->
                        <div class="item monitor-item" @mouseenter="showMonitorSubMenu" @mouseleave="hideMonitorSubMenu"
                            style="text-decoration: none; position: relative;">
                            <img src="../assets/monitor-监控.svg" alt="monitor" class="menu-icon">
                            <span class="text" v-show="!isSidebarCollapsed">监控</span>
                        </div>

                        <RouterLink to="/dispatch" class="item" style="text-decoration: none;">
                            <img src="../assets/dispatch-调度.svg" alt="dispatch" class="menu-icon">
                            <span class="text" v-show="!isSidebarCollapsed">调度</span>
                        </RouterLink>

                        <!-- Business Menu Item with Hover Submenu -->
                        <div class="item business-item" @mouseenter="showBusinessSubMenu"
                            @mouseleave="hideBusinessSubMenu" style="text-decoration: none; position: relative;">
                            <img src="../assets/business-业务.svg" alt="business" class="menu-icon">
                            <span class="text" v-show="!isSidebarCollapsed">业务</span>
                        </div>

                        <RouterLink to="/settings" class="item" style="text-decoration: none;">
                            <img src="../assets/settings-设置.svg" alt="settings" class="menu-icon">
                            <span class="text" v-show="!isSidebarCollapsed">设置</span>
                        </RouterLink>
                    </div>
                </div>

                <div class="footer" v-show="!isSidebarCollapsed">
                    <div class="footer-text">
                        @MoniSafe.2024<br>
                        Bank monitor platform is a solution for finance
                    </div>
                </div>
            </el-aside>

            <!-- Toggle Sidebar Button -->
            <div class="sidebar-toggle" @click="toggleSidebar" :style="{ left: isSidebarCollapsed ? '80px' : '250px' }">
                <img v-if="isSidebarCollapsed" src="@/assets/keyboard_arrow_right-弹出.svg" alt="Expand Sidebar" />
                <img v-else src="@/assets/keyboard_arrow_left-折叠.svg" alt="Collapse Sidebar" />
            </div>

            <el-container
                :style="{ marginLeft: isSidebarCollapsed ? '30px' : '30px', transition: 'margin-left 0.3s ease' }">
                <!-- header -->
                <el-header class="monisafe-header">
                    <div class="header-content">
                        <div class="welcome-message">
                            <h1>Welcome back, {{ currentUsername }}!</h1>
                        </div>

                        <div class="atm-forex-btn">
                            <slot name="atm-forex"></slot>
                        </div>

                        <div class="header-icons">
                            <el-dropdown @command="handleCommand">
                                <div class="icon-button">
                                    <img src="@/assets/switch-切换状态.svg" alt="Switch Icon" class="button-icon">
                                </div>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item command="空闲">空闲</el-dropdown-item>
                                        <el-dropdown-item command="忙碌">忙碌</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>

                            <RouterLink to="/log" class="icon-button" style="text-decoration: none;">
                                <img src="@/assets/ring-日志消息.svg" alt="Ring Icon" class="button-icon">
                            </RouterLink>

                            <RouterLink to="/user/login" class="icon-button" style="text-decoration: none;">
                                <img src="@/assets/exit-退出.svg" alt="Exit Icon" class="button-icon" @click="exit">
                            </RouterLink>
                           
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

        <!-- monitor-submenu placed outside the container -->
        <div class="monitor-submenu" :class="{ collapsed: isSidebarCollapsed }" v-if="isMonitorHovered"
            @mouseenter="showMonitorSubMenu" @mouseleave="hideMonitorSubMenu">
            <RouterLink to="/monitor" class="submenu-item">主监控</RouterLink>
            <RouterLink to="/monitor/node" class="submenu-item">节点</RouterLink>
            <RouterLink to="/monitor/plat" class="submenu-item">地图</RouterLink>
        </div>

        <!-- business-submenu placed outside the container -->
        <div class="business-submenu" :class="{ collapsed: isSidebarCollapsed }" v-if="isBusinessHovered"
            @mouseenter="showBusinessSubMenu" @mouseleave="hideBusinessSubMenu">
            <RouterLink to="/business/map" class="submenu-item">地图</RouterLink>
            <RouterLink to="/business/chart/atm" class="submenu-item">图表</RouterLink>
        </div>
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
    height: 97vh;
    overflow: hidden;
    border-radius: 10px;
    /* background-color: #dce9f3; */
    /* background-color: #E7EAF1; */
    /* background-color: #e8e4e4ca; */
    /* background: radial-gradient(circle at top, #bfdcffdf, transparent); */
    /* background: 
        radial-gradient(circle at top, rgba(255, 0, 0, 0.5), transparent),
        linear-gradient(to bottom right, blue, green); */
    /* background-color: #B0D4F0; */
    /* background-color: #E8F3FB; */
    /* background-color: #E8F3EB; */
    /* background: linear-gradient(to bottom, #80A4CB 0%,#dfeefe 25%, #e5f1fa); */
    /* background: linear-gradient(to bottom, #ecf5fc 0%, #FFFFFF 45%, #FFFFFF 80%, #ECF5FC 100%); */
}

.monisafe-sidebar {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 0;
    transition: width 0.3s ease;
    /* background-color: #85aede; */
    border-radius: 15px;
    background-color: #dce9f3;
}

.business-content {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}

.logo-item-container {
    display: flex;
    justify-content: center;
    padding: 10px;
    transition: text-align 0.3s ease;
}

.logo {
    height: auto;
    transition: width 0.3s ease;
}

.menu-items {
    display: flex;
    flex-direction: column;
    align-items: center;
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
    width: 90%;
    display: flex;
    justify-content: center;
    cursor: pointer;
    padding: 10px;
    margin: 10px;
    transition: justify-content 0.3s ease;
}

.item .menu-icon {
    width: 28px;
    height: 28px;
    margin-right: 10px;
}

.item .text {
    font-size: 20px;
    color: black;
    transition: opacity 0.3s ease;
}

.item:hover {
    background-color: #B0D4F0;
    border-radius: 8px;
}

.business-item,
.monitor-item {
    position: relative;
}

.monitor-submenu {
    position: fixed;
    /* 固定位置 */
    top: 140px;
    /* 根据需要调整弹窗的垂直位置 */
    left: 260px;
    /* 将弹窗放置在业务栏的右侧 */
    background-color: #F0F8FF;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    z-index: 2000;
    /* 提高 z-index 确保弹窗显示在header和body上方 */
}

.monitor-submenu.collapsed {
    top: 100px;
    left: 90px;
    /* 当侧边栏收起时，将弹窗左移到收起后的边栏右侧 */
}

.business-submenu {
    position: fixed;
    /* 固定位置 */
    top: 300px;
    /* 根据需要调整弹窗的垂直位置 */
    left: 260px;
    /* 将弹窗放置在业务栏的右侧 */
    background-color: #F0F8FF;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    z-index: 2000;
    /* 提高 z-index 确保弹窗显示在header和body上方 */
}

.business-submenu.collapsed {
    top: 230px;
    left: 90px;
    /* 当侧边栏收起时，将弹窗左移到收起后的边栏右侧 */
}

.submenu-item {
    padding: 10px 20px;
    text-decoration: none;
    color: black;
    transition: background-color 0.3s ease;
}

.submenu-item:hover {
    background-color: #B0D4F0;
    border-radius: 5px;
}

.footer {
    text-align: center;
    color: #909399;
    font-size: 12px;
    border-top: 1px solid #e4e7ed;
    padding-top: 20px;
}

.footer-text {
    font-family: "Libre Baskerville", serif;
    font-weight: 400;
    font-style: normal;
}

/* 控制侧边栏的按钮 */
.sidebar-toggle {
    width: 10px;
    height: 40px;
    background-color: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    transition: left 0.3s ease, opacity 0.3s ease;
    z-index: 1000;
    opacity: 0;
}

.sidebar-toggle:hover,
.monisafe-sidebar:hover+.sidebar-toggle {
    opacity: 1;
}

.sidebar-toggle span {
    font-size: 20px;
}

.monisafe-header {
    padding: 10px 20px;
    height: auto;
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.welcome-message {
    font-size: 20px;
    font-weight: bold;
}

.welcome-message h1 {
    margin: 10px;
    font-size: 24px;
    font-weight: bold;
}

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
    cursor: pointer;
}

.icon-button:hover {
    background-color: #e0e0e0;
}

.button-icon {
    width: 30px;
    height: 30px;
}

.monisafe-main {
    height: calc(100% - 64px);
    border-radius: 20px;
}
</style>
