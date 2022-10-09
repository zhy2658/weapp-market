<template>
  <el-card>
    <el-row :gutter="20" class="header">
        <el-col :span="7">
          <el-input placeholder="请输入用户昵称..." clearable v-model="queryForm.query"></el-input>
        </el-col>
        <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button>
        
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />

      <el-table-column prop="nickName" label="用户昵称" width="200" />
      <el-table-column  prop="avatarUrl" label="头像" width="200">
        <template v-slot="scope">
          <img :src="filePathHandler(scope.row.avatarUrl)" width="50" height="50"/>
        </template>
      </el-table-column>
      <el-table-column prop="nickName" label="昵称" />

      <!-- <el-table-column prop="registerDate" label="注册日期" width="200"/> -->

      <el-table-column prop="lastLoginDate" label="最后登录日期" width="200"/>

      <el-table-column prop="admin" label="权限" width="100">
        <template v-slot="scope">
          {{adminOption(scope.row.admin)}}
        </template>
        </el-table-column>

      <el-table-column prop="isshow" label="是否公开" width="100">
        <template v-slot="scope">
          {{showOption(scope.row.isshow)}}
        </template>
      </el-table-column>

      <el-table-column prop="action" label="操作" width="250" fixed="right">
        <template v-slot="scope">
         <el-button type="primary" @click="handleShowStatus(scope.row.openid, 1)">公开</el-button>
          <el-button type="danger" @click="handleShowStatus(scope.row.openid, 0)">不公开</el-button>
          <el-button type="danger" @click="handleAdminStatus(scope.row.openid, 0)">降权</el-button>
          <!-- <el-button type="primary" @click="handleShowStatus(scope.row.openid, 0)">提权</el-button>
          <el-button type="danger" @click="handleShowStatus(scope.row.openid, 0)">降权</el-button> -->
          <!-- <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button> -->
        </template>

      </el-table-column> 
    </el-table>

    <el-pagination
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
    </el-pagination>

  </el-card>

</template>

<script setup>
import {Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {getServerUrl,filePathHandler} from "@/config/sys";
import {showOption,adminOption} from "@/config/option.js";
import  axios from '@/util/axios'
import {ElMessageBox,ElMessage} from 'element-plus'





const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

const total=ref(0)

const tableData=ref([

])



const initUserList=async()=>{
  const res=await axios.post("admin/user/allAdmin",queryForm.value);
  tableData.value=res.data.userList;
  total.value=res.data.total;
}

initUserList();

const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initUserList();
}

const handleCurrentChange=(pageNum)=>{
  queryForm.value.pageNum=pageNum;
  initUserList();
}
const handleShowStatus = (openId,isshow) => {
  console.log(openId,isshow)
  ElMessageBox.confirm(
    '您确定要更新该用户的公开状态',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {

      let res=await axios.get("admin/user/update_isshow",{"openId":openId,"isshow":isshow});
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '执行成功！',
        });
        initUserList();
      }else{
        ElMessage({
          type: 'error',
          message: res.data.msg,
        });
      }

    })
    .catch(() => {

    })
}
const handleAdminStatus = (openId,admin) => {
  console.log(openId,admin)
  ElMessageBox.confirm(
    '您确定要更新该用户的公开状态',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {

      let res=await axios.get("admin/user/update_admin",{"openId":openId, "admin": admin});
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '执行成功！',
        });
        initUserList();
      }else{
        ElMessage({
          type: 'error',
          message: res.data.msg,
        });
      }

    })
    .catch(() => {

    })
}

</script>

<style lang="scss" scoped>

.header{
  padding-bottom: 16px;
  box-sizing: border-box;
}

.el-pagination{
  padding-top: 15px;
  box-sizing: border-box;
}

</style>