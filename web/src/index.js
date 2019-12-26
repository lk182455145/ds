// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './config/router'
import store from './vuex'
import ElementUi from 'element-ui'
import { AjaxPlugin } from './plugins'
import 'element-ui/lib/theme-chalk/index.css'
import http from './config/http'

Vue.config.productionTip = false
Vue.use(ElementUi)
Vue.use(AjaxPlugin, http)

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => {
    return h(App)
  }
}).$mount('#app')
