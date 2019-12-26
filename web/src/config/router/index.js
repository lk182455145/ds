import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/app',
    name: 'app',
    component: () => import('@/components/app')
  }, {
    path: '/app/:id',
    name: 'appForm',
    props: true,
    component: () => import('@/components/app/app')
  }, {
    path: '/cnn',
    name: 'cnn',
    component: () => import('@/components/cnn')
  }, {
    path: '/cnn/:id',
    name: 'cnnForm',
    props: true,
    component: () => import('@/components/cnn/cnn')
  }, {
    path: '/svc',
    name: 'svc',
    component: () => import('@/components/svc')
  }, {
    path: '/svc/:id',
    name: 'svcForm',
    props: true,
    component: () => import('@/components/svc/svc')
  }]
})
