import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'//首页组件
import Goods from '../components/Goods'//商品模块组件
import User from '../components/User'


import Details from '../components/Details';

import Transport from '../components/Transport';

import DefectRegistry from '../components/DefectRegistry';
import ReturnRegistry from '../components/ReturnRegistry';

import AdminLog from '../components/AdminLog';
import Lack from '../components/Lack.vue';
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/adminLog', meta: { access: 'all' }
    },
    {
      path: '/home',
      component: Home,
      meta: { access: 'all' }
    },//首页路由
    {
      path: '/goods', component: Goods, meta: { access: 'all' }
    },//商品模块
    { path: '/lack', component: Lack, meta: { access: 'super' } },
    {
      path: '/user', component: User, meta: { access: 'log' }
    },

    { path: '/details', component: Details, meta: { access: 'log' } },
    {
      path: '/transport', component: Transport, meta: { access: 'super' },
      
    },

    { path: '/return', component: ReturnRegistry, meta: { access: 'super' } },
    { path: '/defect', component: DefectRegistry, meta: { access: 'super' } },



    { path: '/adminLog', component: AdminLog, meta: { access: 'all' } },
  ]
})

//router.beforeEach((to, from, next) => {
  //if ((to.meta.access == 'all') || (to.meta.access == 'super' && Vue.prototype.$logUser != null && Vue.prototype.$logUser.type == 0)
    //|| (to.meta.access == 'log' && Vue.prototype.$logUser != null)) { //判断是否需要鉴权
    //next()
  //} else {
    //alert("权限不够或未登录");
 // }

//})

export default router