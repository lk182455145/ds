<template>
  <div>
    <router-link class="button button-primary" to="/svc/new">新增服务</router-link>
    <el-row>
      <el-form :inline="true" style="float: right">
        <el-form-item style="margin-bottom: 0px;">
          <el-input type="text" v-model="searchObj.search" placeholder="名称或描述"/>
        </el-form-item>
      </el-form>
    </el-row>
    <ele-data-tables :ajax="tableUrl" :server-params="searchObj">
      <el-table-column prop="name" label="服务名称"/>
      <el-table-column prop="description" label="服务描述"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="'/svc/'+scope.row.id">编辑</router-link>
        </template>
      </el-table-column>
    </ele-data-tables>
  </div>
</template>
<script>
  import { Component } from 'vue-property-decorator'
  import Vue from 'vue'
  // import { UrlCfg } from '@/services'
  import EleDataTables from 'element-datatables'
  import { State } from 'vuex-class'

  @Component({
    components: {
      EleDataTables
    }
  })
  export default class SvcList extends Vue {
    @State(state => state.url.svc)
    tableUrl
    searchObj = {}
  }
</script>
<style lang="less">

</style>
