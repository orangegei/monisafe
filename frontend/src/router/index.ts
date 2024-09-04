import { createRouter, createWebHistory } from 'vue-router'
import InfoView from '@/views/settings/UpdateInfoView.vue'
import ModifyView from '@/views/settings/UpdatePwdView.vue'
import SecuritylogView from '@/views/settings/SecuritylogView.vue'
import LoginView from '../views/login/LoginView.vue';
import RegisterView from '../views/login/RegisterView.vue';
import ForgetPasswordView from '../views/login/ForgetPasswordView.vue';
import MapView from '@/views/business/MapView.vue'
import ATMInfoView from '@/views/business/ATMInfoView.vue'
import ForexInfoView from '@/views/business/ForexInfoView.vue';
import MonitorView from '@/views/monitor/MonitorView.vue'
import LogInfoView from '@/views/log/LogInfoView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
      redirect: '/business/map'
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
    },
    {
      path: '/business/map',
      name: 'map',
      component: MapView
    },
    {
      path: '/business/chart/atm',
      name: 'atm',
      component: ATMInfoView
    },
    {
      path: '/business/chart/forex',
      name: 'forex',
      component: ForexInfoView
    },
    {
      path: '/monitor',
      name: 'monitor',
      component: MonitorView
    },
    {
      path: '/log',
      name: 'log',
      component: LogInfoView
    }
  ]
})

export default router
