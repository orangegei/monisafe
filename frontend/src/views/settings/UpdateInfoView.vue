<script setup>
import SettingsFrameView from './SettingsFrameView.vue';
</script>

<template>
    <SettingsFrameView>
        <template #main-body>
            <div class="container">
                <h1>个人信息</h1>
                <div class="msg">
                    <div class="profile">
                        <img src="../../assets/profile-头像.jpeg" alt="Profile Icon"
                            style="width: 80px; height: 80px; border-radius: 50%;">
                    </div>

                    <div class="username">
                        <div class="text">用户名</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/username-联系人.svg" alt="icon" style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <div class="email">
                        <div class="text">邮箱</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/email-邮箱.svg" alt="icon" style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <div class="phoneNumber">
                        <div class="text">手机号码</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/phone-手机号.svg" alt="icon" style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <button class="modify-btn">
                        更新信息
                    </button>
                </div>
            </div>
        </template>
    </SettingsFrameView>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 30px 50px;
}

.msg {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 450px;
    border-radius: 20px;
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

.profile,
.username,
.email,
.phoneNumber {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 20px 0;
    gap: 15px;
}

.info-area {
    display: flex;
    flex-direction: row;
    justify-content: center;
    gap: 15px;
}

.text {
    font-size: 18px;
    text-align: left;
}

.input-area {
    width: 300px;
    height: 45px;
    border: 1px solid rgb(128, 128, 128);
    border-radius: 5px;
    background-color: transparent;
    caret-color: #77a2cc;
    font-size: 18px;
    padding-left: 15px;
}

.modify-btn {
    margin: 30px 0;
    width: 150px;
    height: 40px;
    border: 1px solid rgb(128, 128, 128);
    border-radius: 5px;
    background-color: #11569A;
    font-size: 16px;
    color: white;
    cursor: pointer;
}
</style>