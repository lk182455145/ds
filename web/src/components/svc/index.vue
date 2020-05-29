<template>
  <div>
    <router-link class="button button-primary" to="/svc/new">新增服务</router-link>
    <el-row>
      <el-form :inline="true" style="float: right">
        <el-form-item style="margin-bottom: 0px;">
          <el-input type="text" v-model="searchObj.search" placeholder="名称或描述" />
        </el-form-item>
      </el-form>
    </el-row>
    <ele-data-tables ref="table" :ajax="tableUrl" :server-params="searchObj">
      <el-table-column prop="name" label="服务名称">
        <template v-slot="{row}">
          <router-link :to="{name:'svcView',params:{id:row.id}}">{{row.name}}</router-link>&nbsp;
        </template>
      </el-table-column>
      <el-table-column prop="description" label="服务描述" />
      <el-table-column label="操作">
        <template slot-scope="{row}">
          <router-link :to="'/svc/'+row.id">编辑</router-link>&nbsp;
          <a href="javascript:void(0)" @click="delSvc(row)">删除</a>
        </template>
      </el-table-column>
    </ele-data-tables>
  </div>
</template>
<script>
  import {Component} from 'vue-property-decorator'
  import Vue from 'vue'
  // import { UrlCfg } from '@/services'
  import EleDataTables from 'element-datatables'

  const svcUrl = CONTEXT_PATH + 'svc'

  @Component({
    components: {
      EleDataTables
    }
  })
  export default class SvcList extends Vue {
    tableUrl = svcUrl

    searchObj = {}

    delSvc (svc) {
      this.$confirm('此操作将永久删除该服务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return this.$http.delete(`${svcUrl}/${svc.id}`)
      }).then(() => {
        this.$refs.table.reloadData()
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
</script>
<style lang="less">

</style>
