
import Vue from 'vue'
import axios from 'axios'
import App from './App'
import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Router from 'vue-router'

Vue.prototype.$http=axios;
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(Router)
Vue.prototype.$logUser = null
Vue.prototype.$dynamicHref="http://localhost:8081"

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  beforeCreate () { // 尽量早的执行挂载全局事件总线对象的操作
    Vue.prototype.$globalEventBus = this
    },
  template: '<App/>'
})
