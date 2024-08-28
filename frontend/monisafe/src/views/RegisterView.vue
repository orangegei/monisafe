<template>
    <!-- 注册表单 -->
    <div class="register-page">
        <el-card class="register-card">

            <!-- 标题 -->
            <div class="header">
                <img src="@/assets/logo.png" alt="Logo" class="logo" />
                <h1>银行监控告警系统</h1>
            </div>

            <!-- 注册组件 -->
            <el-form :model="registerForm" label-position="top" class="form-container">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="registerForm.email" placeholder="请输入邮箱" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phoneNumber">
                    <el-input v-model="registerForm.phoneNumber" placeholder="请输入电话号码" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" class="short-input"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="blue-button" @click="register">注册</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>
  

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';

export default {
    data() {
        return {
            registerForm: {
                username: '',
                password: '',
                email: '',
                phoneNumber: '',
            },
        };
    },
    methods: {
        register() {
            axios.post('/user/register', this.registerForm)
                .then(response => {
                    console.log(response.data);
                    if(response.data.code === 0) {
                        ElMessage.success("注册成功");
                        this.$router.push({ path: '/login' });
                    } else {
                        ElMessage.error(response.data.message || "注册失败");
                    }
                })
                .catch(error => {
                    console.error('请求发送失败', error);
                    ElMessage.error('网络错误，请稍后重试');
                });
        }
    },
};
</script>
  

<style scoped>
.register-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}
  
.register-card {
    width: 400px;
    padding: 20px;
    background-color: rgba(255, 255, 255, 0.6);
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); 
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
  
.form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}
  
.short-input {
    width: 300px;
}
  
.send-code-button {
    margin-left: 10px;
    border-radius: 5px;
}
  
.blue-button {
    display: block;
    width: 30%;
    margin: 0 auto;
    border-radius: 25px;
    text-align: center; 
    padding: 10px 20px; /* 确保按钮内边距适中 */
}
  
.input-group {
    display: flex;
    align-items: center;
    gap: 10px;
}
</style>