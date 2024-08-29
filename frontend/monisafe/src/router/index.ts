import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/Login/LoginView.vue';
import RegisterView from '../views/Login/RegisterView.vue';
import ForgetPasswordView from '../views/Login/ForgetPasswordView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/user/login' },
    { path: '/user/login', name: 'login',component: LoginView },
    { path: '/user/register', name: 'register',component: RegisterView },
    { path: '/user/forgetPassword', name: 'forgetPassword',component: ForgetPasswordView }
  ]
})

export default router
