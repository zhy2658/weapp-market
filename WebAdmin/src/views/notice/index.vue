<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入标题..." clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button>
      <el-button type="primary" @click="handleDialogValue()">添加</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />

      <el-table-column prop="title" label="标题" width="200" />
      <!-- <el-table-column  prop="message" label="内容" width="200">
        <template v-slot="scope">
          <img :src="filePathHandler(scope.row.avatarUrl)" width="50" height="50"/>
        </template>
      </el-table-column> -->
      <el-table-column prop="message" label="内容" />

      <!-- <el-table-column prop="registerDate" label="注册日期" width="200"/> -->

      <el-table-column prop="n_time" label="发布时间" width="200" />
      <el-table-column prop="top" label="是否置顶" width="100">
        <template v-slot="scope">
          {{showIsTop(scope.row.top)}}
          <!-- {{scope.row.top}} -->
        </template>
      </el-table-column>

      <el-table-column prop="action" label="操作" width="250" fixed="right">
        <template v-slot="scope">
          <!-- <el-button type="primary" @click="handleShowStatus(scope.row.openid, 1)">公开</el-button>
          <el-button type="danger" @click="handleShowStatus(scope.row.openid, 0)">不公开</el-button> -->
          <el-button type="primary" @click="handleDialogValue(scope.row)">编辑</el-button>
          <!-- <el-button type="danger" @click="handleAdminStatus(scope.row.openid, 0)">删除</el-button> -->
        </template>

      </el-table-column>
    </el-table>

    <el-pagination v-model:currentPage="queryForm.pageNum" :page-sizes="[10, 20, 30, 40,50]"
      :page-size="queryForm.pageSize" :small="small" :disabled="disabled" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange">
    </el-pagination>

  </el-card>
  <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle" @initUserList="initUserList" :id="id" />


</template>

<script setup>
import { Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { getServerUrl, filePathHandler } from "@/config/sys";
import { showOption, adminOption, showIsTop } from "@/config/option.js";
import axios from '@/util/axios'
import Dialog from './components/dialog'
import { ElMessageBox, ElMessage } from 'element-plus'

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

// 弹出框
const id = ref(-1)

const dialogTitle = ref('')

const total = ref(0)

const tableData = ref([

])



const initUserList = async () => {
  const res = await axios.post("admin/notice/list", queryForm.value);
  tableData.value = res.data.notieList;
  total.value = res.data.total;
}

initUserList();

const dialogVisible=ref(false)

const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initUserList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initUserList();
}
const addNotice = () => {

}

const handleDialogValue = (row) => {
  if (row) {
    id.value = row.id;
    console.log("update", id)
    dialogTitle.value = "通知修改"
  } else {
    console.log("add")
    id.value = -1;
    dialogTitle.value = "通知添加"
  }
  dialogVisible.value = true;
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