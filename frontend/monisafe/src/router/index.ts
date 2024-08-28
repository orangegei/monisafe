import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/user/login' },
    { path: '/user/login', name: 'login',component: LoginView },
    { path: '/user/register', name: 'register',component: RegisterView }
  ]
})

export default router
