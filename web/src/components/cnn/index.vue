<template>
  <div>
    <router-link class="button button-primary" to="/cnn/new">新增连接</router-link>
    <ele-data-tables :ajax="tableUrl">
      <el-table-column prop="name" label="连接名称" />
      <el-table-column prop="dbType" label="数据库类型" />
      <el-table-column prop="url" label="连接地址" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="'/cnn/'+scope.row.id">编辑</router-link>
        </template>
      </el-table-column>
    </ele-data-tables>
  </div>
</template>
<script>
  import Vue from 'vue'
  import EleDataTables from 'element-datatables'
  import { Component } from 'vue-property-decorator'
  import { State } from 'vuex-class'

  @Component({
    components: {
      EleDataTables
    }
  })
  export default class CnnList extends Vue {
    @State(state => state.url.cnn)
    tableUrl
  }
</script>
<style lang="less">

</style>
