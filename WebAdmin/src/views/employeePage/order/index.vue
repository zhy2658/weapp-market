<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入订单号..." clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button>
    </el-row>
    <div>
      <div class="demo">
        <span>筛选：</span>
        <el-select v-model="status" @change="selectPlatform">
          <el-option v-for="(item, index) in orderStatusOption()" :key="index" :value='index' :label='item' v-show="index!=0">{{ item }}
          </el-option>
        </el-select>


      </div>
    </div>
    <br>
    <div>当前显示: <span style="color:darkcyan;">{{ orderStatusOption()[status] }}的订单</span></div><br>
    <el-table :data="tableData" stripe style="width: 100%">

      <el-table-column prop="orderNo" label="订单号" width="250" fixed/>

      <el-table-column prop="wxUserInfo" label="用户昵称" width="200" :formatter="wxUserInfoNickNameFormatter" />

      <el-table-column prop="totalPrice" label="订单总价" width="100" />

      <el-table-column prop="status" label="订单状态" width="100" :formatter="statusFormatter"/>

      <el-table-column prop="createDate" label="订单创建日期" width="200" />

      <el-table-column prop="payDate" label="订单支付日期" width="200" />

      <!-- <el-table-column prop="consignee" label="微信号" width="80" /> -->

      <!-- <el-table-column prop="telNumber" label="联系电话" width="150" /> -->

      <!-- <el-table-column prop="servant_id" label="接单员工" width="150" /> -->

      <!-- <el-table-column prop="address" label="收货地址" width="400" /> -->

      <el-table-column prop="action" label="操作" width="100" fixed="right">
        <template v-slot="scope">
         <el-button type="success"  @click="handleDialogValue(scope.row)">详情</el-button>
          <!-- <el-button type="danger" >退款</el-button> -->
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

  <Dialog v-model="dialogVisible"  @initOrdereList="initOrdereList"  :id="id"/>

</template>

<script setup>

import {Search,Edit,Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import  axios from '@/util/axios'
import {getServerUrl,filePathHandler} from "@/config/sys";
import Dialog from './components/dialog'
import { orderStatusOption} from "@/config/option.js";

import {ElMessageBox,ElMessage} from 'element-plus'


// const statusOption = orderStatusOption();

let status = ref(2)

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
  queryForm.value.status=status;
  queryForm.value.page=queryForm.value.pageNum;
  const res=await axios.get("order/manage/getMyOrder",queryForm.value);
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
const selectPlatform = (value) => {
  initOrderList();
  // this.$forceUpdate()//强制更新
  // console.log("event -------------------   ",value)
  // this.value = this.queryParams
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
  return orderStatusOption()[status];
  // 0 未支付 1 已经支付正在服务  2完成服务，待确认，3完成订单  4请求退单 5：已退单
  // switch (status) {
  //   case 0:
  //     return "未支付";
  //   case 1:
  //     return "正在服务";
  //   case 2:
  //     return "完成服务";
  //   case 3:
  //     return "完成订单";
  //   case 4:
  //     return "请求退单";
  //   case 5:
  //     return "已退单";
  // }
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