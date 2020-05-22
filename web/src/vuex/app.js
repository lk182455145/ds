import Vue from 'vue'

export default {
  namespaced: true,
  actions: {
    save ({ rootState: { url } }, obj) {
      return Vue.http.post(url.app, obj)
    },
    get ({ rootState: { url } }, id) {
      return Vue.http.get(url.app + '/' + id)
    },
    update ({ rootState: { url } }, obj) {
      return Vue.http.put(url.app + `/${obj.id}`, obj)
    }
  }
}
