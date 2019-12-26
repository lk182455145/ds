<template>
  <el-form :model="cnn" ref="cnnForm" label-width="100px">
    <el-row>
      <el-col :span="24">连接信息</el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="连接名称" prop="name">
          <el-input v-model="cnn.name"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="数据库类型" prop="name">
          <el-select v-model="cnn.dbType">
            <el-option key="SQLSERVER" value="SQLSERVER"/>
            <el-option key="MYSQL" value="MYSQL"/>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="连接URL" prop="name">
          <el-input v-model="cnn.url"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="cnn.username"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="密码" prop="name">
          <el-input type="password" v-model="cnn.password"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-form-item>
        <el-button type="primary" @click="submit()">提交</el-button>
        <el-button type="danger" @click="back()">取消</el-button>
      </el-form-item>
    </el-row>
  </el-form>
</template>
<script>
  import Vue from 'vue'
  import { Component, Prop } from 'vue-property-decorator'

  import { Action } from 'vuex-class'

  @Component({})
  export default class CnnForm extends Vue {
    @Prop({default: 'new'})
    id
    cnn = {}

    @Action('cnn/get')
    load

    @Action('cnn/state')
    checkState

    @Action('cnn/update')
    update

    @Action('cnn/save')
    save

    created () {
      if (this.id === 'new') {

      } else {
        this.load(this.id).then(cnn => {
          this.cnn = cnn
        })
      }
    }

    back () {
      this.$router.go(-1)
    }

    submit () {
      this.checkState(this.cnn).then(result => {
        if (result) {
          if (this.id === 'new') {
            this.save(this.cnn).then(response => {
              this.$router.go(-1)
            })
          } else {
            this.update({id: this.id, data: this.cnn}).then(response => {
              this.$router.go(-1)
            })
          }
        } else {
          this.$message.error('连接校验失败，不能保存')
        }
      })
    }
  }
</script>
<style>

</style>
