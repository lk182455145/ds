<template>
  <div>
    <el-form ref="svcForm" label-width="100px">
      <el-row>
        <el-col :span="24">服务信息</el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="name" label="服务名称">
            <el-input v-model="svc.name" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
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
        <el-col :span="12">
          <el-form-item label="描述">
            <el-input type="textarea" v-model="svc.description" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item prop="sql" label="SQL语句">
            <el-input type="textarea" v-model="svc.sql" @blur="getMeta()" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item />
      <div>
        <el-row>
          <el-col :span="24">条件列</el-col>
        </el-row>
        <el-row v-for="(parameter,index) in svc.parameters" :key="index">
          <!--<el-col>{{ parameter }},{{ index }}</el-col>-->
          <el-col :span="5">{{ parameter.column }}</el-col>
          <el-col :span="5">
            <el-select v-model="parameter.operator">
              <el-option value="=" />
              <el-option value=">" />
              <el-option value="<" />
              <el-option value=">=" />
              <el-option value="<=" />
              <el-option value="!=" />
              <el-option label="部分包含" value="like" />
              <el-option label="选项之一" value="in" />
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-input v-model="parameter.parameterName" />
          </el-col>
          <el-col :span="5">
            <a href="javascript:;" @click="removeParameter(index)">移除</a>
          </el-col>
        </el-row>
      </div>

      <div>
        <el-row>
          <el-col :span="24">排序列</el-col>
        </el-row>
        <el-row v-for="(order,index) in svc.orders" :key="index">
          <el-col :span="5">{{ order.column }}</el-col>
          <el-col :span="10">
            <el-select v-model="order.direction">
              <el-option label="升序" value="ASC" />
              <el-option label="降序" value="DESC" />
            </el-select>
          </el-col>
          <el-col :span="5">
            <a href="javascript:;" @click="removeOrder(index)">移除</a>
          </el-col>
        </el-row>
      </div>

      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-button type="danger" @click="goback()">取消</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-row>
      <el-form>
        <ele-data-tables :data="columns">
          <el-table-column prop="columnName" label="列名称" />
          <el-table-column prop="columnTypeName" label="数据类型" />
          <el-table-column label="说明">
            <template v-slot="{row:{columnName}}">
              <el-form-item>
                <el-input v-model="svc.columns[columnName]" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <a href="javascript:;" @click="addToParameter(scope.row)">添加到筛选</a>&nbsp;
              <a href="javascript:;" @click="addToOrder(scope.row)">添加到排序</a>
            </template>
          </el-table-column>
        </ele-data-tables>
      </el-form>
    </el-row>
  </div>
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
        (async () => {
          this.svc = await this.getSvc(this.id)
          this.columns = await this.getColumns(this.svc)
        })()
      }
    }

    addToParameter (row) {
      if (this.svc.parameters) {
      } else {
        this.$set(this.svc, 'parameters', [])
      }
      this.svc.parameters.push({
        column: row.columnName,
        operator: '=',
        parameterName: row.columnName
      })
    }

    addToOrder (row) {
      if (this.svc.orders) {
      } else {
        this.$set(this.svc, 'orders', [])
      }
      this.svc.orders.push({
        column: row.columnName,
        direction: 'ASC'
      })
    }

    removeOrder (index) {
      this.svc.orders.splice(index, 1)
    }

    removeParameter (index) {
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
        this.getColumns(this.svc).then(columns => {
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
