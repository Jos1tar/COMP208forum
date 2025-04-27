import { createRouter, createWebHistory } from 'vue-router';

// 导入组件
import Home from '../components/home.vue'; // 假设你的首页组件叫 Home.vue
import Login from '../components/Login.vue'; // 假设你的登录/注册组件叫 Login.vue

// 定义路由规则
const routes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
  },
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;