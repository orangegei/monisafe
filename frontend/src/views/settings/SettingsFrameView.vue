<script setup>

</script>

<template>
    <div class="common-layout">
        <el-container style="height: 100%;">
            <el-aside width="250px" class="settings-sidebar">
                <RouterLink to="/monitor" class="logo-item-container" style="text-decoration: none; color: inherit;">
                    <img src="../../assets/logo-MoniSafe-透明.png" alt="MoniSafe Logo" class="logo">
                    <span>返回</span>
                </RouterLink>

                <div class="profile">
                    <div class="picture">
                        <img src="../../assets/profile-头像.jpeg" alt="Profile Icon"
                            style="width: 75px;height: 75px;border-radius: 50%;">
                    </div>
                    <div>
                        <h1>username</h1>
                    </div>
                </div>

                <div>
                    <div class="settings-items">
                        <RouterLink to="/user/settings/updateInfo" class="item" style="text-decoration: none;">
                            <img src="../../assets/person-个人信息.svg" alt="person-info" class="menu-icon">
                            <span class="text">个人信息</span>
                        </RouterLink>
                        <RouterLink to="/user/settings/updatePwd" class="item" style="text-decoration: none;">
                            <img src="../../assets/account-management-账户管理.svg" alt="account-pwd" class="menu-icon">
                            <span class="text">修改密码</span>
                        </RouterLink>
                        <RouterLink to="/user/settings/securitylog" class="item" style="text-decoration: none;">
                            <img src="../../assets/secure-log-安全日志.svg" alt="secure-log" class="menu-icon">
                            <span class="text">用户日志</span>
                        </RouterLink>
                    </div>
                </div>
            </el-aside>

            <el-main style="background-color: aliceblue;">
                <slot name="main-body"></slot>
            </el-main>
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
}

.settings-sidebar {
    background-color: #f9fbfd;
    display: flex;
    flex-direction: column;
    padding: 10px;
    gap: 20px;
}

.settings-sidebar .logo-item-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 16px;
    cursor: pointer;
}

.logo {
    width: 70px;
    height: auto;
}

.profile {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f9fbfd;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 10px;
    gap: 20px;
}

.picture {
    display: flex;
    justify-content: center;
    align-items: center;
}

.settings-items {
    display: flex;
    flex-direction: column;
    margin-top: 10px;
}

.settings-items .item {
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    padding: 10px;
    margin: 10px;
}

.item .menu-icon {
    width: 24px;
    height: 24px;
    margin-right: 10px;
}

.item .text {
    font-size: 16px;
    color: #333;
}

.item:hover {
    background-color: #E2E6F9;
    border-radius: 8px;
}
</style>