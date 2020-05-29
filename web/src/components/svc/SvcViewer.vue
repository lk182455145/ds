<template>
  <el-collapse v-model="active">
    <el-collapse-item name="1">
      <template v-slot:title>
        <h1>服务信息</h1>
      </template>
      <div>服务名称: {{svc.name}}</div>
      <div>服务描述：</div>
      <div>{{svc.description}}</div>
    </el-collapse-item>
    <el-collapse-item name="2">
      <template v-slot:title>
        <h1>参数列表</h1>
      </template>
      <ele-data-tables :data="parameters">
        <el-table-column prop="parameterName" label="参数名称" />
        <el-table-column prop="required" label="是否必要" />
        <el-table-column prop="description" label="参数说明" />
      </ele-data-tables>
    </el-collapse-item>

    <el-collapse-item name="3">
      <template v-slot:title>
        <h1>输出字段</h1>
      </template>
      <ele-data-tables :data="meta.columns">
        <el-table-column prop="columnLabel" label="字段名称" />
        <el-table-column prop="columnTypeName" label="值类型" />
        <el-table-column prop="description" label="参数说明">
          <template v-slot="{row:{columnLabel}}">
            {{svc.columns[columnLabel]}}
          </template>
        </el-table-column>
      </ele-data-tables>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
  import Vue from 'vue'
  import {Component, Prop} from 'vue-property-decorator'
  import EleDataTables from 'element-datatables'

  const svcUrl = CONTEXT_PATH + 'svc'
  @Component({
    components: {
      EleDataTables
    }
  })
  export default class SvcViewer extends Vue {
    active = ['1', '2', '3']
    @Prop({required: true})
    id

    svc = {}
    meta = {}

    get parameters () {
      return [
        ...this.svc.requiredParameters.map(item => ({
          ...item,
          required: '是'
        })),
        ...this.svc.parameters.map(item => ({
          ...item,
          required: '否'
        }))
      ]
    }

    created () {
      this.$http.get(`${svcUrl}/${this.id}`).then(svc => (this.svc = svc))
      this.$http.get(`${svcUrl}/meta/${this.id}`).then(meta => (this.meta = meta))
    }
  }
</script>

<style scoped>

</style>
