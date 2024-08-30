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
            <el-form :model="registerForm" :rules="rules" label-position="top" class="form-container" ref="form">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="emailAddress">
                    <el-input v-model="registerForm.emailAddress" placeholder="请输入邮箱" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phoneNumber">
                    <el-input v-model="registerForm.phoneNumber" placeholder="请输入电话号码" class="short-input"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" class="short-input"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class="button-group">
                        <el-button type="default" @click="$router.push('/user/login')" class="return-button">返回</el-button>
                        <el-button type="primary" class="blue-button" @click="register">注册</el-button>
                    </div>
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
                emailAddress: '',
                phoneNumber: '',
            },
            rules: {
                emailAddress: [
                    { 
                        required: true, 
                        message: '请输入邮箱', 
                        trigger: 'blur' 
                    },
                    { 
                        type: 'email', 
                        message: '邮箱格式不正确', 
                        trigger: 'blur' 
                    }
                ],
                phoneNumber: [
                    { 
                        required: true, 
                        message: '请输入电话号码', 
                        trigger: 'blur' 
                    },
                    { 
                        pattern: /^[0-9]{11}$/, 
                        message: '电话号码必须是11位数字', 
                        trigger: 'blur' 
                    }
                ],
                password: [
                    { 
                        required: true, 
                        message: '请输入密码', 
                        trigger: 'blur' 
                    },
                    { 
                        min: 6, 
                        max: 16, 
                        message: '密码长度应在6到16个字符之间', 
                        trigger: 'blur' 
                    }
                ],
            },
        };
    },
    methods: {
        register() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    axios.post('http://localhost:8081/user/register', this.registerForm)
                        .then(response => {
                            console.log(response.data);
                            if (response.data.code === 0) {
                                ElMessage.success("注册成功");
                                this.$router.push({ path: '/user/login' });
                            } else {
                                ElMessage.error(response.data.message || "注册失败");
                            }
                        })
                        .catch(error => {
                            console.error('请求发送失败', error);
                            ElMessage.error('网络错误，请稍后重试');
                        });
                } else {
                    ElMessage.error('请填写正确的信息');
                }
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

.button-group {
    display: flex;
    justify-content: space-between;
    width: 100%;
}

.return-button {
    width: 40%;
    border-radius: 25px;
    text-align: center;
    padding: 10px 20px;
}

.blue-button {
    width: 40%;
    border-radius: 25px;
    text-align: center;
    padding: 10px 20px;
}
</style>
