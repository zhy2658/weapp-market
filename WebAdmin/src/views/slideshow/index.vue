<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <!-- <el-col :span="7">
        <el-input placeholder="请输入标题..." clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button> -->
      <el-button type="primary" @click="handleDialogValue()">添加</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />

      <el-table-column prop="type" label="类型" width="200" :formatter="statusFormatter" />
      <!-- <el-table-column  prop="message" label="内容" width="200">
        <template v-slot="scope">
          <img :src="filePathHandler(scope.row.avatarUrl)" width="50" height="50"/>
        </template>
      </el-table-column> -->
      <!-- <el-table-column prop="imgSrc" label="轮播图" width="150"  show-overflow-tooltip="true"  /> -->
      <el-table-column prop="imgSrc" label="幻灯图片" width="200" align="center">
        <template v-slot="{ row }">
          <img v-if="row.imgSrc" :src="getServerUrl() + '/image/swiper/' + row.imgSrc" height="75" />
          <div v-else style="color:red">请上传幻灯图片</div>
        </template>
      </el-table-column>

      <el-table-column prop="appPage" label="小程序页面" width="150"  show-overflow-tooltip="true"  />

      <el-table-column prop="employeeId" label="员工号" width="100"   />

      <el-table-column prop="url" label="URL链接" width="150"  show-overflow-tooltip="true"  />

      <!-- <el-table-column prop="registerDate" label="注册日期" width="200"/> -->
      <el-table-column prop="sort" label="序号" width="200" />

      <!-- <el-table-column prop="create_time" label="发布时间" width="200" /> -->

      <el-table-column prop="action" label="操作" width="300" fixed="right">
        <template v-slot="scope">
          <!-- <el-button type="primary" @click="handleShowStatus(scope.row.openid, 1)">公开</el-button>
          <el-button type="danger" @click="handleShowStatus(scope.row.openid, 0)">不公开</el-button> -->
          <el-button type="primary" @click="handleChangeSwiper(scope.row)">幻灯图片</el-button>
          <el-button type="primary" @click="handleDialogValue(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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
  <SwiperDialog v-model="swiperDialogVisible" :id="id" :swiperSort="swiperSort" @initProductList="initUserList" />
  

</template>

<script setup>
import { Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { getServerUrl, filePathHandler } from "@/config/sys";
import { showOption, adminOption, showIsTop } from "@/config/option.js";
import SwiperDialog from './components/swiperDialog'
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
const swiperDialogVisible = ref(false)
const swiperSort = ref(-1)


const initUserList = async () => {
  const res = await axios.post("admin/slideshow/list", queryForm.value);
  tableData.value = res.data.slideShowList;
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
    console.log("update", id.value)
    dialogTitle.value = "轮播修改"
  } else {
    console.log("add")
    id.value = -1;
    dialogTitle.value = "轮播添加"
  }
  dialogVisible.value = true;
}

const statusFormatter = (row) => {
  let type=row.type;
  // 0 未支付 1 已经支付正在服务  2完成服务，待确认，3完成订单  4请求退单 5：已退单
  switch (type) {
    case 0:
      return "员工";
    case 1:
      return "链接";
    case 2:
      return "小程序页面";
  }
}
const handleChangeSwiper = (row) => {
  id.value = row.id;
  swiperSort.value = row.swiperSort;
  swiperDialogVisible.value = true;
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
    console.log("delete id=" + id)
    let res = await axios.get("admin/slideshow/delete/" + id);
    if (res.data.code == 0) {
      ElMessage({
        type: 'success',
        message: '删除成功！',
      });
      initUserList();
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