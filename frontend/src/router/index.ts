import { createRouter, createWebHistory } from 'vue-router'
import FrameView from '@/views/FrameView.vue'
import InfoView from '@/views/settings/UpdateInfoView.vue'
import ModifyView from '@/views/settings/UpdatePwdView.vue'
import SecuritylogView from '@/views/settings/SecuritylogView.vue'
import ATMView from '@/views/business/ATMView.vue'
import ForexView from '@/views/business/ForexView.vue'
import MonitorView from '@/views/monitor/MonitorView.vue'
import LoginView from '../views/login/LoginView.vue';
import RegisterView from '../views/login/RegisterView.vue';
import ForgetPasswordView from '../views/login/ForgetPasswordView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/frame',
      name: 'frame',
      component: FrameView
    },
    {
      path: '/settings',
      redirect: '/user/settings/updateInfo'
    },
    {
      path: '/user/settings/updateInfo',
      name: 'updateInfo',
      component: InfoView
    },
    {
      path: '/user/settings/updatePwd',
      name: 'updatePwd',
      component: ModifyView
    },
    {
      path: '/user/settings/securitylog',
      name: 'securitylog',
      component: SecuritylogView
    },
    {
      path: '/business',
      redirect: '/business/atm'
    },
    {
      path: '/business/atm',
      name: 'atm',
      component: ATMView
    },
    {
      path: '/business/forex',
      name: 'forex',
      component: ForexView
    },
    {
      path: '/monitor',
      name: 'monitor',
      component: MonitorView
    },
    {
      path: '/',
      redirect: '/user/login'
    },
    {
      path: '/user/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/user/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/user/forgetPassword',
      name: 'forgetPassword',
      component: ForgetPasswordView
    }
  ]
})

export default router
