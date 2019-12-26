import Vue from 'vue'
// import UrlCfg from '../services/url-configuration-service'

export default {
  namespaced: true,
  actions: {
    get ({rootState: {url}}, id) {
      return Vue.http.get(url.svc + '/' + id)
    },
    getColumns ({rootState: {url}}, svc) {
      return Vue.http.post(url.svc + '/meta', svc)
    },
    save ({rootState: {url}}, obj) {
      return Vue.http.post(url.svc, obj)
    },
    update ({rootState: {url}}, {id, svc}) {
      return Vue.http.put(url.svc + `/${id}`, svc)
    }
  }
}
