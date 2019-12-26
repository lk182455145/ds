<template>
  <div id="app" v-loading.fullscreen.lock="loading">
    <el-container>
      <el-aside width="200px">
        <el-menu router>
          <el-menu-item index="1" route="/app">
            <i class="el-icon-menu"/>
            <span slot="title">应用管理</span>
          </el-menu-item>
          <el-menu-item index="2" route="/cnn">
            <i class="el-icon-setting"/>
            <span slot="title">连接管理</span>
          </el-menu-item>
          <el-menu-item index="3" route="/svc">
            <i class="el-icon-setting"/>
            <span slot="title">服务管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  // import { mapState } from 'vuex'
  import Vue from 'vue'
  import { Component, Watch } from 'vue-property-decorator'

  @Component({
    name: 'app'
  })
  export default class App extends Vue {
    get loading () {
      return this.$store.state.loadingCount > 0
    }

    get error () {
      return this.$store.state.error
    }

    @Watch('error', {immediate: true, deep: true})
    onErrorChange (newValue, oldValue) {
      if (newValue.count > 0) {
        this.$notify({
          title: '提示',
          message: newValue.message,
          duration: 5000
        })
      }
    }
  }
</script>
<style>

</style>
