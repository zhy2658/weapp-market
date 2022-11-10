<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入PID" clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button>
      <el-button type="primary" @click="handleDialogValue()">新增</el-button>
    </el-row>
    <div>
      <div class="demo">
        <span>筛选：</span>
        <el-select v-model="status" @change="selectPlatform">
          <el-option v-for="(item, index) in statusOption" :key="index" :value='index' :label='item'>{{ item }}
          </el-option>
        </el-select>


      </div>
    </div>
    <br>
    <div>当前显示: <span style="color:darkcyan;">{{ statusOption[status] }}的帖子</span></div><br>

    <el-table :data="tableData" stripe style="width: 100%"  class="tableData">

      <el-table-column prop="pid" label="#PID" width="70" fixed />

      <el-table-column prop="title" label="标题" width="150" />

      <el-table-column prop="content" label="内容" width="300"   />

      <el-table-column prop="userInfo.address" label="地址" width="100" />

      <el-table-column prop="userInfo.nickName" label="创建人" width="100" />

      <el-table-column prop="status" label="状态" width="100" :formatter="statusFormatter" />


      <el-table-column prop="pubtime" label="创建时间" width="100" />

      <el-table-column prop="action" label="操作" width="300" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleDialogValue(scope.row)">查看</el-button>
          <!-- <el-button type="primary" @click="handlePublishStatus(scope.row.pid, 1)">通过</el-button> -->
          <el-button type="danger" @click="handleDelete(scope.row.pid)">删除</el-button>
          <!-- <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button> -->
        </template>

      </el-table-column>

    </el-table>

    <el-pagination v-model:currentPage="queryForm.pageNum" :page-sizes="[10, 20, 30, 40, 50]"
      :page-size="queryForm.pageSize" :small="small" :disabled="disabled" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange">
    </el-pagination>
  </el-card>

  <Dialog v-model="dialogVisible" @initOrdereList="initOrdereList" :id="id" :currentRow="currentRow"
    :dialogTitle="dialogTitle" />
</template>

<script setup>

import { Search, Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from '@/util/axios'
import { getServerUrl } from "@/config/sys";
import { showOption, adminOption, sexOption } from "@/config/option.js";
import Dialog from './components/dialog'


import { ElMessageBox, ElMessage } from 'element-plus'

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})


const total = ref(0)

const statusOption = ["未审核", "已通过", "未通过"]

const tableData = ref([
])

const id = ref(-1)
const currentRow = ref({})

let status = ref(1)


const initOrderList = async () => {
  queryForm.value.status = status._value;
  const res = await axios.post("publish/get_mypublish", queryForm.value);
  console.log(res);
  tableData.value = res.data.publishs;
  total.value = res.data.total;
}

const selectPlatform = (value) => {
  initOrderList();
  // this.$forceUpdate()//强制更新
  // console.log("event -------------------   ",value)
  // this.value = this.queryParams
}


initOrderList();

const dialogVisible = ref(false)
const dialogTitle = ref("")



const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initOrderList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initOrderList();
}


const handleDialogValue = (row) => {
  // id.value=row.pid;
  // currentRow.value=row;
  // dialogVisible.value=true;
  if (row) {
    id.value = row.pid;
    console.log("update", id)
    dialogTitle.value = "查看帖子"
  } else {
    console.log("add")
    id.value = -1;
    dialogTitle.value = "帖子添加"
  }
  dialogVisible.value = true;

}


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
      console.log("id=" + id)
      let res = await axios.get("publish/delete/" + id);
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
    '您确定要更新这帖子状态吗?',
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

const wxUserInfoNickNameFormatter = (row) => {
  return row.wxUserInfo.nickName;
}



const statusFormatter = (row) => {
  let status = row.status;
  return statusOption[status];
  // switch (status) {
  //   case 1:
  //     return "待支付";
  //   case 2:
  //     return "待发货"
  //   case 3:
  //     return "退款/退货"
  // }
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