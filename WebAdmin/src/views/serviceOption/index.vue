<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <!-- <el-col :span="7">
        <el-input placeholder="请输入PID" clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button> -->
      <el-button type="primary" @click="handleDialogValue()">新增</el-button>
    </el-row>
    <div>
      <div class="demo">
        <span>筛选：</span>
        <el-select v-model="status" @change="selectPlatform">
          <el-option v-for="(item, index) in memberGradeOption()" :key="index" :value='index' :label='item'>{{ item }}
          </el-option>
        </el-select>
        <!-- {{memberGradeOption}} -->


      </div>
    </div>
    <br>
    <div>当前显示: <span style="color:darkcyan;">{{ memberGradeOption()[status] }}</span></div><br>

    <el-table :data="tableData" stripe style="width: 100%">

      <el-table-column prop="id" label="#ID" width="70" fixed />
      <el-table-column prop="itemName" label="项目名" width="300" />

      <el-table-column prop="price" label="价格(米粒)" width="100">
        <template v-slot="scope">
          {{ scope.row.price }}
        </template>
      </el-table-column>

      <el-table-column prop="itemHours" label="小时数" width="150" />

      <el-table-column prop="create_time" label="创建时间" width="200" />

      <el-table-column prop="grade" label="等级" width="100" />

      <el-table-column prop="required" label="必选" width="100">
        <template v-slot="scope">
          {{ payitemRequiredOption()[scope.row.required] }}
        </template>
      </el-table-column>

      <!-- <el-table-column prop="pubtime" label="创建时间" width="100" /> -->

      <!-- <el-table-column prop="wxUserInfo" label="用户昵称" width="200" :formatter="wxUserInfoNickNameFormatter" />

      <el-table-column prop="totalPrice" label="订单总价" width="100" />

      <el-table-column prop="status" label="订单状态" width="100" :formatter="statusFormatter"/>

      <el-table-column prop="createDate" label="订单创建日期" width="200" />

      <el-table-column prop="payDate" label="订单支付日期" width="200" />

      <el-table-column prop="consignee" label="收货人" width="80" />

      <el-table-column prop="telNumber" label="联系电话" width="150" />

      <el-table-column prop="address" label="收货地址" width="400" />
      -->

      <el-table-column prop="action" label="操作" width="200" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleDialogValue(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          <!-- <el-button type="primary" @click="handlePublishStatus(scope.row.pid, 1)">通过</el-button>
          <el-button type="danger" @click="handlePublishStatus(scope.row.pid, 2)">拒绝</el-button> -->
          <!-- <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button> -->
        </template>

      </el-table-column>

    </el-table>

    <!-- <el-pagination
      v-model:currentPage="queryForm.pageNum"
      :page-sizes="[10, 20, 30, 40,50]"
      :page-size="queryForm.pageSize"
      :small="small"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination> -->
    <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle" @initOrderList="initOrderList" :id="id" />

  </el-card>

  <Dialog v-model="dialogVisible" @initOrderList="initOrderList" :id="id" :currentRow="currentRow" :status="status" />

</template>

<script setup>

import { Search, Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from '@/util/axios'
import { getServerUrl } from "@/config/sys";
import { showOption, adminOption, memberGradeOption, payitemRequiredOption } from "@/config/option.js";
import Dialog from './components/dialog'

import { ElMessageBox, ElMessage } from 'element-plus'

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

const total = ref(0)

// const statusOption= ["1级员工","2级员工","3级员工","4级员工","5级员工"]

const tableData = ref([
])

const id = ref(-1)
// 弹出框

const dialogTitle = ref('')
const currentRow = ref({})

let status = ref(1)


const initOrderList = async () => {
  const res = await axios.post("/admin/playitem/getByGrade?grade=" + status._value, queryForm.value);
  console.log(res);
  tableData.value = res.data.payitemList;
  // total.value=res.data.total;
}

const selectPlatform = (value) => {
  initOrderList();
  // this.$forceUpdate()//强制更新
  // console.log("event -------------------   ",value)
  // this.value = this.queryParams
}


initOrderList();

const dialogVisible = ref(false)



const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initOrderList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initOrderList();
}


// const handleDialogValue = (row) => {
//   id.value=row.pid;
//   currentRow.value=row;
//   dialogVisible.value=true;
// }

const handleDelete = (id) => {

  ElMessageBox.confirm(
    '您确定要删除这条记录吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      console.log("delete id=" + id)
      let res = await axios.get("admin/playitem/delete/" + id);
      if (res.data.code == 0) {
        ElMessage({
          type: 'success',
          message: '删除成功！',
        });
        initOrderList();
      } else {
        ElMessage({
          type: 'error',
          message: res.data.msg,
        });
      }

    })
    .catch(() => {

    })
}

const handlePublishStatus = (pid, status) => {
  console.log(pid, status)
  ElMessageBox.confirm(
    '您确定要更新这个帖子状态吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {

      let res = await axios.get("admin/publish/update_publish_status", { "pid": pid, "status": status });
      if (res.data.code == 0) {
        ElMessage({
          type: 'success',
          message: '执行成功！',
        });
        initOrderList();
      } else {
        ElMessage({
          type: 'error',
          message: res.data.msg,
        });
      }

    })
    .catch(() => {

    })
}

const handleDialogValue = (row) => {
  if (row) {
    id.value = row.id;
    console.log("update", id)
    dialogTitle.value = "选项修改"
  } else {
    console.log("add")
    id.value = -1;
    dialogTitle.value = "选项添加"
  }
  dialogVisible.value = true;
}

const wxUserInfoNickNameFormatter = (row) => {
  return row.wxUserInfo.nickName;
}

const statusFormatter = (row) => {
  let status = row.status;
  switch (status) {
    case 1:
      return "待支付";
    case 2:
      return "待发货"
    case 3:
      return "退款/退货"
  }
}


</script>

<style lang="scss" scoped>
.header {
  padding-bottom: 16px;
  box-sizing: border-box;
}

.el-pagination {
  padding-top: 15px;
  box-sizing: border-box;
}
</style>