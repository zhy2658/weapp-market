<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入用户名称..." clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initProductList">搜索</el-button>
      <!-- <el-button type="primary" @click="handleDialogValue()">添加用户</el-button> -->
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">

      <el-table-column prop="userInfo.nickName" label="昵称" width="100" fixed/>

      <el-table-column prop="userInfo.avatarUrl" label="头像" width="150"  >
        <template v-slot="{row}">
          <img :src="filePathHandler(row.userInfo.avatarUrl)" width="80" height="80"/>
        </template>
      </el-table-column>

      <!-- <el-table-column prop="price" label="价格" width="100" /> -->

      <!-- <el-table-column prop="stock" label="库存" width="100" /> -->

      <el-table-column prop="userInfo.smallType" :formatter="typeFormatter" label="分类" width="200">
      <template v-slot="{row}">
            <span v-if="row.userInfo.smallType">{{row.userInfo.smallType.name}}</span>
        </template>
      </el-table-column>

      <el-table-column prop="hot" label="主页展示?" width="100" >
        <template v-slot="{row}">
          <el-switch v-model="row.hot" @change="hotChangeHandle(row)"/>
        </template>
      </el-table-column>

      <el-table-column prop="swiper" label="首页幻灯？" width="100" align="center">
        <template v-slot="{row}">
          <el-switch v-model="row.swiper" @change="hotSwiperChangeHandle(row)"/>
        </template>
      </el-table-column>

      <el-table-column prop="swiperPic" label="幻灯图片" width="200" align="center">
        <template v-slot="{row}">
          <img :src="getServerUrl()+'/image/swiper/'+row.swiperPic" width="150" height="75" />
        </template>
      </el-table-column>

      <el-table-column prop="swiperSort" label="幻灯排序" width="100" align="center"/>

      <el-table-column prop="description" label="描述" width="400" />

      <el-table-column prop="action" label="操作" width="500" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleChangeImage(scope.row)">更换图片</el-button>
          <el-button type="primary" @click="handleChangeSwiper(scope.row)">幻灯设置</el-button>
          <el-button type="primary" :icon="Edit" @click="handleDialogValue(scope.row)"></el-button>
          <!-- <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button> -->
          <el-button type="primary" @click="handleChangeProductSwiperImage(scope.row)">轮播图片设置</el-button>
          <el-button type="primary" @click="handleAdminStatus(scope.row.userInfo.openid, 1)">提权</el-button>
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

  <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle" @initProductList="initProductList"  :dialogValue="dialogValue"/>
  <ImageDialog v-model="imageDialogVisible" :id="id" :uid="uid"  @initProductList="initProductList"/>
  <SwiperDialog v-model="swiperDialogVisible" :id="id" :swiperSort="swiperSort" @initProductList="initProductList" />
  <ProductSwiperImageDialog :productId="id" v-model="productSwiperImageDialogVisible"></ProductSwiperImageDialog>
</template>

<script setup>

import {Search,Edit,Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import  axios from '@/util/axios'
import {getServerUrl,filePathHandler} from "@/config/sys";
import Dialog from './components/dialog'
import ImageDialog from './components/imageDialog'
import SwiperDialog from './components/swiperDialog'
import ProductSwiperImageDialog from './components/productSwiperImageDialog'

import {ElMessageBox,ElMessage} from 'element-plus'

const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

const total=ref(0)

const id=ref(-1)
const uid=ref(-1)

const swiperSort=ref(-1)

const tableData=ref([
])

const dialogValue=ref({})

const dialogTitle=ref('')

  const initProductList=async()=>{
  // console.log(queryForm.value)
  const res=await axios.post("admin/product/list",queryForm.value);
  // console.log("list ----",res.data.productList)
  tableData.value=res.data.productList;
  total.value=res.data.total;
}

initProductList();

const dialogVisible=ref(false)

const imageDialogVisible=ref(false)

const swiperDialogVisible=ref(false)

const productSwiperImageDialogVisible=ref(false)


const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initProductList();
}

const handleCurrentChange=(pageNum)=>{
  queryForm.value.pageNum=pageNum;
  initProductList();
}


const handleDialogValue = (row) => {
  if(row){
    dialogValue.value=JSON.parse(JSON.stringify(row));
    dialogTitle.value="用户修改"
  }else{
    dialogValue.value={
      productIntroImgs:'',
      productParaImgs:'',
      type:{
        id:""
      }
    }
    dialogTitle.value="用户添加"
  }
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
      let res=await axios.get("admin/product/delete/"+id);
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '删除成功！',
        });
        initProductList();
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


const typeFormatter = (row) => {
  return row.type.name
}



const hotChangeHandle = async (row) => {
  let res=await axios.get("admin/product/updateHot/"+row.id+"/state/"+row.hot);
  if(res.data.code==0){
    ElMessage({
      type: 'success',
      message: '执行成功！',
    });
  }else{
    ElMessage({
      type: 'error',
      message: res.data.msg,
    });
    initProductList();
  }
}


const hotSwiperChangeHandle = async (row) => {
  let res=await axios.get("admin/product/updateSwiper/"+row.id+"/state/"+row.swiper);
  if(res.data.code==0){
    ElMessage({
      type: 'success',
      message: '执行成功！',
    });
  }else{
    ElMessage({
      type: 'error',
      message: res.data.msg,
    });
    initProductList();
  }
}

const handleChangeImage = (row) => {
  id.value=row.id;
  uid.value=row.userInfo.id;
  imageDialogVisible.value=true;
}

const handleChangeSwiper = (row) => {
  id.value=row.id;
  uid.value=row.userInfo.id;
  swiperSort.value=row.swiperSort;
  swiperDialogVisible.value=true;
}

const handleChangeProductSwiperImage = (row) => {
  id.value=row.id;
  uid.value=row.userInfo.id;
  productSwiperImageDialogVisible.value=true;
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