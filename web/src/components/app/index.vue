<template>
  <div>
    <router-link class="button button-primary" to="/app/new">新建应用</router-link>
    <ele-data-tables :ajax="tableUrl">
      <el-table-column prop="name" label="应用名称"/>
      <el-table-column prop="id" label="应用TOKEN"/>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          <div>{{ scope.row.createDate|today }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="'/app/'+scope.row.id">编辑</router-link>
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
    },
    filters: {
      today (val) {
        let date = new Date(val)
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
      }
    }
  })
  export default class AppList extends Vue {
    @State(state => state.url.app)
    tableUrl
  }
</script>
<style lang="less" scoped>

</style>
