<template>
  <el-form ref="svcForm" label-width="100px">
    <el-collapse v-model="active">
      <el-collapse-item name="1">
        <template v-slot:title>
          <h1>服务信息</h1>
        </template>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="name" label="服务名称">
              <el-input v-model="svc.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="connectionId" label="服务连接">
              <el-select v-model="svc.connectionId" @change="getMeta()">
                <el-option :label="cnn.name"
                           :value="cnn.id"
                           :key="cnn.name"
                           v-for="cnn in cnns">
                  <span>{{ cnn.name }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="描述">
              <el-input type="textarea" v-model="svc.description" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item prop="sql" label="SQL语句">
              <el-input type="textarea" v-model="svc.sql" @blur="getMeta()" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-collapse-item>

      <el-collapse-item name="2">
        <template v-slot:title>
          <h1>条件参数</h1>
        </template>
        <ele-data-tables :data="svc.parameters">
          <el-table-column prop="column" label="列名称" />
          <el-table-column label="比较符">
            <template v-slot="{ row }">
              <el-form-item label-width="0px">
                <el-select v-model="row.operator">
                  <el-option value="=" />
                  <el-option value=">" />
                  <el-option value="<" />
                  <el-option value=">=" />
                  <el-option value="<=" />
                  <el-option value="!=" />
                  <el-option label="部分包含" value="like" />
                  <el-option label="选项之一" value="in" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="参数名称">
            <template v-slot="{row}">
              <el-form-item label-width="0px">
                <el-input v-model="row.parameterName" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="{row}">
              <a href="javascript:;" @click="{{removeParameter(row)}}">移除</a>
            </template>
          </el-table-column>
        </ele-data-tables>
      </el-collapse-item>
      <el-collapse-item name="3">
        <template v-slot:title>
          <h1>默认排序</h1>
        </template>

        <ele-data-tables :data="svc.orders">
          <el-table-column prop="column" label="列名称"></el-table-column>
          <el-table-column label="排序方式">
            <template v-slot="{row}">
              <el-form-item label-width="0px">
                <el-select v-model="row.direction" style="width:100%;">
                  <el-option label="升序" value="ASC" />
                  <el-option label="降序" value="DESC" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="{row}">
              <a href="javascript:;" @click="removeOrder(row)">移除</a>
            </template>
          </el-table-column>
        </ele-data-tables>
      </el-collapse-item>

      <el-collapse-item name="4">
        <template v-slot:title>
          <h1>输出字段</h1>
        </template>
        <ele-data-tables :data="columns">
          <el-table-column prop="columnLabel" label="列名称" />
          <el-table-column prop="columnTypeName" label="数据类型" />
          <el-table-column label="说明">
            <template v-slot="{row:{columnLabel}}">
              <el-input v-model="svc.columns[columnLabel]" />
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <a href="javascript:;" @click="addToParameter(scope.row)">添加到筛选</a>&nbsp;
              <a href="javascript:;" @click="addToOrder(scope.row)">添加到排序</a>
            </template>
          </el-table-column>
        </ele-data-tables>
      </el-collapse-item>
    </el-collapse>
    <el-row>
      <el-col :span="24">
        <el-button type="primary" @click="submit()">提交</el-button>
        <el-button type="danger" @click="goback()">取消</el-button>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
  import Vue from 'vue'
  import { Component, Prop } from 'vue-property-decorator'
  import EleDataTables from 'element-datatables'
  import { Action } from 'vuex-class'

  @Component({
    components: {
      EleDataTables
    }
  })
  export default class SvcForm extends Vue {
    @Prop({ default: 'new' })
    id

    active = ['1', '2', '3', '4']

    columns = []
    cnns = []
    svc = {
      columns: {}
    }

    @Action('svc/get')
    getSvc

    @Action('svc/getColumns')
    getColumns

    @Action('svc/save')
    save

    @Action('svc/update')
    update

    @Action('cnn/list')
    listConnections

    created () {
      this.listConnections().then(cnns => {
        this.cnns = cnns
      })

      if (this.id === 'new') {
        // do nothing
      } else {
        this.getSvc(this.id)
          .then(svc => (this.svc = svc))
          .then(this.getMeta)
      }
    }

    addToParameter (row) {
      if (this.svc.parameters) {
      } else {
        this.$set(this.svc, 'parameters', [])
      }
      this.svc.parameters.push({
        column: row.columnLabel,
        operator: '=',
        parameterName: row.columnLabel
      })
    }

    addToOrder (row) {
      if (this.svc.orders) {
      } else {
        this.$set(this.svc, 'orders', [])
      }
      this.svc.orders.push({
        column: row.columnLabel,
        direction: 'ASC'
      })
    }

    vis (obj) {
      return obj.row
    }

    removeOrder (row) {
      const index = this.svc.orders.findIndex(item => item === row)
      this.svc.orders.splice(index, 1)
    }

    removeParameter (row) {
      const index = this.svc.parameters.findIndex(item => item === row)
      this.svc.parameters.splice(index, 1)
    }

    submit () {
      if (!this.svc.orders || this.svc.orders.length < 1) {
        this.$message.error('必须至少指定一个排序列')
        return
      }
      if (this.id === 'new') {
        this.save(this.svc).then(svc => {
          this.$router.go(-1)
        })
      } else {
        this.update({ id: this.id, svc: this.svc }).then(svc => {
          this.$router.go(-1)
        })
      }
    }

    getMeta () {
      if (this.svc.connectionId && this.svc.sql) {
        this.getColumns(this.svc).then(({ columns }) => {
          this.columns = columns
        })
      }
    }

    goback () {
      this.$router.go(-1)
    }
  }
</script>
<style lang="less">
</style>
