import Vue from 'vue'

export default {
  namespaced: true,
  actions: {
    save ({rootState: {url}}, obj) {
      return Vue.http.post(url.cnn, obj)
    },
    get ({rootState: {url}}, id) {
      return Vue.http.get(url.cnn + '/' + id)
    },
    update ({rootState: {url}}, {id, data}) {
      return Vue.http.put(url.cnn + `/${id}`, data)
    },
    state ({rootState: {url}}, obj) {
      return Vue.http.get(url.cnn + '/state', {params: obj})
    },
    list ({rootState: {url}}) {
      return Vue.http.get(url.cnn)
    }
  }

}
