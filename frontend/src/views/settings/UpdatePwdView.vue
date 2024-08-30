<script lang="ts" setup>
import SettingsFrameView from './SettingsFrameView.vue';
</script>

<template>
    <SettingsFrameView>
        <template #main-body>
            <div class="container">
                <h1>修改密码</h1>

                <div class="msg">
                    <div class="oldPwd">
                        <div class="text">原密码</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/visibility-off-密码不可见.svg" alt="icon"
                                    style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <div class="newPwd">
                        <div class="text">新密码</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/visibility-off-密码不可见.svg" alt="icon"
                                    style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <div class="rePwd">
                        <div class="text">确认新密码</div>
                        <div class="info-area" style="position: relative;">
                            <input type="text" class="input-area" style="padding-right: 30px;">
                            <span class="icon"
                                style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">
                                <img src="../../assets/visibility-off-密码不可见.svg" alt="icon"
                                    style="width: 20px; height: 20px;">
                            </span>
                        </div>
                    </div>

                    <button class="modify-btn">确认修改</button>
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
    background-color: #F5F6F8;
}

.oldPwd,
.newPwd,
.rePwd {
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