<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <!-- <el-col :span="7">
        <el-input placeholder="请输入PID" clearable v-model="queryForm.query"></el-input>
      </el-col> -->
      <!-- <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button> -->
      <div class="block">
        <span class="demonstration">时间：</span>
        <el-date-picker v-model="value1" type="datetimerange"  :shortcuts="shortcuts" range-separator="至"
          start-placeholder="开始日期" end-placeholder="结束日期" align="right">
        </el-date-picker>
      </div>

      <el-button type="primary" :icon="Search" @click="loadPage" style="margin-left:20px">确定</el-button>
      <!-- <el-button type="primary" :icon="Search" @click="exportExcel" style="margin-left:20px">导出</el-button> -->
      <vue3-json-excel class="export-excel-wrapper" :json-data="exportList" :fields="json_fields"
        :name="nameFormatter()">
        <!-- 上面可以自定义自己的样式，还可以引用其他组件button -->
        <el-button type="primary" style="margin-left:20px">导出</el-button>
      </vue3-json-excel>
    </el-row>
    <!-- <div>
      <div class="demo">
        <span>筛选：</span>
        <el-select v-model="status" @change="selectPlatform">
          <el-option v-for="(item, index) in statusOption" :key="index" :value='index' :label='item'>{{ index }}_{{ item
          }}
          </el-option>
        </el-select>
      </div>

    </div> 
    <br>
    <div>当前显示: <span style="color:darkcyan;">{{ statusOption[status] }}的帖子</span></div><br>
-->
    <el-table :data="tableData" stripe style="width: 100%" id="exportTab">
      <!-- "employee_id": "employee_id",  
      "nickName": "nickName",
      "sex":"sex",    //常规字段
      "tel":"tel",
      "employee_grade":"employee_grade",
      "publishCount":"publishCount",
      "randomOrderCount":"randomOrderCount",
      "assignOrderCount":"assignOrderCount",
      "refuseOrderCount":"refuseOrderCount",
      "totalCount":"totalCount",
      "conpainCount":"conpainCount" -->

      <el-table-column prop="employee_id" label="员工号" width="100" fixed />

      <el-table-column prop="nickName" label="昵称" width="150" />

      <el-table-column prop="sex" label="性别" width="100" >
      <template v-slot="scope">
          {{ sexOption(scope.row.sex) }} 
        </template>
      </el-table-column>

      <el-table-column prop="employee_grade" label="员工等级" width="100" />

      <el-table-column prop="publishCount" label="发帖次数" width="100" />

      <el-table-column prop="randomOrderCount" label="随机订单" width="100" />
      <el-table-column prop="assignOrderCount" label="指定订单" width="100" />
      <el-table-column prop="refuseOrderCount" label="拒接订单" width="100" />
      <el-table-column prop="totalCount" label="接单总数" width="100" />
      <el-table-column prop="conpainCount" label="被投诉" width="100" />
      <el-table-column prop="totalRevenue" label="总收入" width="200">
        <template v-slot="scope">
          {{ scope.row.totalRevenue }} 米粒
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

      <!-- <el-table-column prop="action" label="操作" width="300" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleDialogValue(scope.row)">详情</el-button>
          <el-button type="primary" @click="handlePublishStatus(scope.row.pid, 1)">通过</el-button>
          <el-button type="danger" @click="handlePublishStatus(scope.row.pid, 2)">拒绝</el-button>
        </template>

      </el-table-column> -->

    </el-table>

    <el-pagination v-model:currentPage="queryForm.pageNum" :page-sizes="[10, 20, 30, 40, 50]"
      :page-size="queryForm.pageSize" :small="small" :disabled="disabled" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange">
    </el-pagination>
  </el-card>

  <Dialog v-model="dialogVisible" @initOrdereList="initOrdereList" :id="id" :currentRow="currentRow" />

</template>

<script setup>

import { Search, Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from '@/util/axios'
import { getServerUrl } from "@/config/sys";
import Dialog from './components/dialog'
import FileSaver from 'file-saver'
import { showOption, adminOption, sexOption } from "@/config/option.js";



import { ElMessageBox, ElMessage } from 'element-plus'

// console.log("use:", use)

const json_fields = ref({
  "员工号": "employee_id",
  "昵称": "nickName",
  // "性别": "sex",    //常规字段
  "电话": "tel",
  "员工等级": "employee_grade",
  "发帖次数": "publishCount",
  "随机订单": "randomOrderCount",
  "指定订单": "assignOrderCount",
  "拒接订单": "refuseOrderCount",
  "接单总数": "totalCount",
  "被投诉": "conpainCount",
  "总收入": "totalRevenue"

  // Telephone: "phone.mobile", //支持嵌套属性
  // "Telephone 2": {
  //   field: "phone.landline",
  //   //自定义回调函数
  //   callback: value => {
  //     return `Landline Phone - ${value}`;
  //   }
  // }
})
const json_data = ref([
  {
    name: "Tony Peña",
    city: "New York",
    country: "United States",
    birthdate: "1988-03-15",
    phone: {
      mobile: "1-338-888-8888",
      landline: "(541) 754-3010"
    }
  },
  {
    name: "Thessaloniki",
    city: "Athens",
    country: "Greece",
    birthdate: "1987-11-22",
    phone: {
      mobile: "+1 333 888 8888",
      landline: "(2741) 2621-244"
    }
  }
])
const json_meta = ref([
  [
    {
      " key ": " charset ",
      " value ": " utf-8 "
    }
  ]
])

const shortcuts = [
  {
    text: 'Last week',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: 'Last month',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: 'Last 3 months',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  },
]
const value1 = ref(
  [new Date(2022, 9, 10, 10, 10), new Date(2022, 11, 20, 10, 10)]
)
const nameFormatter = () => {

  return formatDateYMD(value1.value[0]) + "---" + formatDateYMD(value1.value[1])
}
const value2 = ref('')


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

let status = ref(0)


const initOrderList = async () => {
  queryForm.value.begin = value1.value[0];
  queryForm.value.end = value1.value[1];
  console.log(queryForm.value)
  const res = await axios.post("admin/statistics/list", queryForm.value);
  console.log("res:", res.data);
  tableData.value = res.data.orderList;
  total.value = res.data.total;
  getExportList();
  // total.value = 100;
}

const exportList = ref([])
const getExportList = async () => {
  const exportQueryForm = {

    pageNum: 1,
    pageSize: 100000,
    begin: value1.value[0],
    end: value1.value[1]
  }

  const res = await axios.post("admin/statistics/list", exportQueryForm);
  exportList.value = res.data.orderList;
  // total.value = 100;
}

const selectPlatform = (value) => {
  initOrderList();
  // this.$forceUpdate()//强制更新
  // console.log("event -------------------   ",value)
  // this.value = this.queryParams
}


initOrderList();

const dialogVisible = ref(false)


// const exportExcel = () => {
//   let XLSX = require("xlsx")
//   /* generate workbook object from table */
//   var xlsxParam = { raw: true } // 导出的内容只做解析，不进行格式转换
//   var wb = XLSX.utils.table_to_book(document.querySelector('#exportTab'), xlsxParam)

//   /* get binary string as output */
//   var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
//   try {
//     FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '员工统计表.xlsx')
//   } catch (e) {
//     if (typeof console !== 'undefined') {
//       console.log(e, wbout)
//     }
//   }
//   return wbout
// }


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
  id.value = row.pid;
  currentRow.value = row;
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
      let res = await axios.get("admin/order/delete/" + id);
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
const loadPage = () => {
  initOrderList();
  console.log(value2.value)
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
var formatDateYMD = function (date) {

  var year = date.getFullYear(),
    month = date.getMonth() + 1,//月份是从0开始的
    day = date.getDate(),
    hour = date.getHours(),
    min = date.getMinutes(),
    sec = date.getSeconds();
  var newTime = year + '-' +
    month + '-' +
    day;
  return newTime;
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