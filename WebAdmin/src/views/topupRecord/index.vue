<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入订单号..." clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">

      <el-table-column prop="topupNo" label="订单号" width="300" fixed/>

      <el-table-column prop="wxUserInfo.nickName" label="用户昵称" width="200"/>

      <el-table-column prop="coinNum" label="充值数量" width="100" />

      <el-table-column prop="totalPrice" label="金额" width="100"/>

      <el-table-column prop="status" label="订单状态" width="100" :formatter="statusFormatter" />

      <el-table-column prop="create_time" label="订单创建日期" width="200" />

      <el-table-column prop="payDate" label="支付时间" width="200" />

  
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

  <Dialog v-model="dialogVisible"  @initOrdereList="initOrdereList"  :id="id"/>

</template>

<script setup>

import {Search,Edit,Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import  axios from '@/util/axios'
import {getServerUrl,filePathHandler} from "@/config/sys";
import Dialog from './components/dialog'

import {ElMessageBox,ElMessage} from 'element-plus'

const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

const total=ref(0)


const tableData=ref([
])

const id=ref(-1)


const initOrderList=async()=>{
  console.log('xxx')
  const res=await axios.post("admin/topup/list",queryForm.value);
  tableData.value=res.data.orderList;
  total.value=res.data.total;
}

initOrderList();

const dialogVisible=ref(false)



const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initOrderList();
}

const handleCurrentChange=(pageNum)=>{
  queryForm.value.pageNum=pageNum;
  initOrderList();
}


const handleDialogValue = (row) => {
  id.value=row.id;
  dialogVisible.value=true;
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
    .then(async() => {
      console.log("id="+id)
      let res=await axios.get("admin/order/delete/"+id);
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '删除成功！',
        });
        initOrderList();
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

const handleOrderStatus = (id,status) => {
  ElMessageBox.confirm(
    '您确定要更新这个订单状态吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {

      let res=await axios.post("admin/order/updateStatus",{id:id,status:status});
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '执行成功！',
        });
        initOrderList();
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

const wxUserInfoNickNameFormatter = (row) => {
  return row.wxUserInfo.nickName;
}

const statusFormatter = (row) => {
  let status=row.status;
  // 0 未支付 1 已经支付正在服务  2完成服务，待确认，3完成订单  4请求退单 5：已退单
  switch (status) {
    case 0:
      return "未支付";
    case 1:
      return "支付成功";
  }
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