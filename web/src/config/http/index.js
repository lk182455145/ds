import axios from 'axios'

import store from '@/vuex'

const http = axios.create()

http.interceptors.request.use(config => {
  store.commit('loading')
  return config
}, error => {
  return Promise.reject(error)
})

http.interceptors.response.use(response => {
  store.commit('loadingComplete')
  return response
}, error => {
  store.commit('loadingComplete')
  return Promise.reject(error)
})

http.interceptors.response.use(({ data }) => {
  return data
}, error => {
  const err = '与服务器交互时出现错误，错误码：' + error.response.status
  store.commit('addError', err)
  return Promise.reject(error)
})

export default http
