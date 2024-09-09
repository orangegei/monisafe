<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import instance from '@/utils/request'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

// 创建响应式表单数据
const loginForm = reactive({
    username: '',
    password: '',
});

const router = useRouter();

// 登录方法
const login = async () => {
    try {
        const response = await instance.post('/user/login', loginForm);
        if (response.data.code === 0) {
            ElMessage.success("登录成功");
            sessionStorage.setItem("token", response.data.data);
            sessionStorage.setItem("isLogin", true);
            router.push({ path: '/monitor' });
        } else {
            ElMessage.error(response.data.message || "登录失败");
        }
    } catch (error) {
        console.error('请求发送失败', error);
        ElMessage.error('网络错误，请稍后重试');
    }
};
</script>

<template>
    <!-- 登录表单 -->
    <div class="login-page">
        <el-card class="login-card">

            <!-- 标题 -->
            <div class="header">
                <img src="@/assets/logo.png" alt="Logo" class="logo" />
                <h1>银行监控告警系统</h1>
            </div>

            <!-- 登录组件 -->
            <el-form :model="loginForm" @submit.prevent="login" label-position="top">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"
                        class="short-input"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="login-button" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
            <div class="auth-buttons">
                <router-link to="/user/register" class="auth-button">注册</router-link>
                <span class="space"> </span>
                <router-link to="/user/forgetPassword" class="auth-button">忘记密码</router-link>
            </div>
        </el-card>
    </div>
</template>

<style scoped>
.login-page {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 5%;
    width: 167ch;
    height: 97.8vh;
    background: url('../../assets/background.png') no-repeat center center;
    background-size: cover;
}

.login-card {
    width: 400px;
    padding: 10px;
    background-color: rgba(255, 255, 255, 0.6);
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    justify-content: flex-start;
}

.header {
    text-align: center;
    margin-bottom: 20px;
}

.logo {
    max-width: 100px;
    height: auto;
    margin-bottom: 10px;
}

.header h1 {
    font-family: 'Arial', sans-serif;
    font-size: 36px;
    color: #333;
}

.short-input {
    width: 400px;
}

.form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.form-item {
    display: flex;
    justify-content: center;
    width: 100%;
}

.login-button {
    display: block;
    width: 30%;
    margin: 0 auto;
    border-radius: 25px;
}

.auth-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.auth-button {
    color: #007bff;
    text-decoration: none;
    font-size: 16px;
}

.auth-button:hover {
    text-decoration: underline;
}

.space {
    flex-grow: 1;
}
</style>
