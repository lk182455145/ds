<template>
  <div>
    <div>应用信息</div>
    <el-form :model="app" ref="appForm" label-width="100px">

      <el-row v-if="id!=='new'">
        <el-col :span="12">
          <el-form-item label="应用ID" prop="id">
            <el-input v-model="app.id" disabled />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="应用名称" prop="name">
            <el-input v-model="app.name" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="应用描述" prop="name">
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="app.description" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-form-item>
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-button type="danger" @click="go()">取消</el-button>
        </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>
<script>
  import Vue from 'vue'
  // import { AppService } from '@/services'
  import {Component, Prop} from 'vue-property-decorator'

  import {Action} from 'vuex-class'

  @Component({
    name: 'app-form'
  })
  export default class extends Vue {
    @Prop({default: ''})
    id

    app = {}

    @Action('app/save')
    save

    @Action('app/get')
    load

    @Action('app/update')
    update

    submit () {
      if (this.id === 'new') {
        this.save(this.app).then(response => {
          this.$router.go(-1)
        })
      } else {
        this.update(this.app).then(() => {
          this.$router.go(-1)
        })
      }
    }

    created () {
      if (this.id === 'new') {

      } else {
        this.load(this.id).then(data => {
          this.app = data
        }).catch(err => {
          console.log('读取数据发生错误', err)
        })
      }
    }

    go () {
      this.$router.go(-1)
    }
  }
</script>
<style lang="less">

</style>
