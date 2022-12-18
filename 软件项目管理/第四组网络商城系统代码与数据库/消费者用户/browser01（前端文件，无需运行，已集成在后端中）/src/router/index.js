import Vue from 'vue'
import Router from 'vue-router'

import Display from '../components/Display';
import Cart from '../components/Cart';
import Details from '../components/Details';
import UserOrder from '../components/UserOrder';

import UserLog from '../components/UserLog';

Vue.use(Router)

const router =  new Router({
  routes: [
    {path: '/',redirect: '/display',meta: {access:'all'}},
    
    
    {
      path: '/userOrder',component: UserOrder,meta: {access:'log'}
      
    },
    {path: '/details',component: Details,meta: {access:'log'}},
  
    
    {path: '/display',component: Display,meta: {access:'all'}},
    {path: '/cart',component: Cart,meta: {access:'log'}},
    {path: '/userLog',component: UserLog,meta: {access:'all'}}

    
  ]
})


router.beforeEach((to,from,next)=>{
	if((to.meta.access=='all') || (to.meta.access=='log'&&Vue.prototype.$logUser!=null)
    ){ //判断是否需要鉴权
    next()
  }else {
        alert("权限不够或未登录")
  }

}) 

export default router