
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
Vue.prototype.$dynamicHref="http://localhost:8082"
Vue.prototype.$refreshBalance=function(){
  this.$http.get(this.$dynamicHref+"/user/refreshBalance/"+this.$logUser.userId).then((res)=>{
    this.$logUser.userBalance=res.data;
    });
};


new Vue({
  el: '#app',
  router,
  components: { App },
  beforeCreate () { 
    Vue.prototype.$globalEventBus = this
    },
  template: '<App/>'
})
