<template>
  <el-dialog
    model-value="dialogVisible"
    title="内容详细"
    width="80%"
    @close="handleClose"
  >

  <div>{{tableData.content}}</div>
  <div style="display:flex;flex-wrap: wrap;">
    <div v-for="(item,index) in tableData.imgList">
      <img :src="filePathHandler(item.src)" style="width:200px;"/>
     
    </div>
  </div>
    <!-- <el-table :data="tableData" stripe style="width: 100%"> -->

      <!-- <el-table-column type="index"   width="50" /> -->

      <!-- <el-table-column prop="goodsPic" label="图片" width="200"  >
        <template v-slot="scope">
          <img :src="getServerUrl()+'/image/product/'+scope.row.goodsPic" width="80" height="80"/>
        </template>
      </el-table-column> -->

      <!-- <el-table-column prop="title" label="标题" /> -->

      <!-- <el-table-column prop="goodsPrice" label="价格" width="100" /> -->

      <!-- <el-table-column prop="goodsNumber" label="数量" width="100" /> -->

    <!-- </el-table> -->
  </el-dialog>
</template>

<script setup>
import { defineEmits,ref ,defineProps,watch} from 'vue'
import axios from "@/util/axios";
import { ElMessage } from "element-plus";
import {getServerUrl,filePathHandler} from "@/config/sys";

const props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  },
  currentRow:{
    type:Object,
    default:{},
    required:true
  }
})


const initOrderDetailData=async(id)=>{
  // const res=await axios.get("admin/orderDetail/list/"+id);
  tableData.value = props.currentRow;
  console.log(" tableData.value", tableData.value)
  
}


watch(
  ()=>props.id,
  ()=>{
    console.log("iiiiiid="+props.id);
    let id=props.id;
    if(id!=-1){
      initOrderDetailData(props.id);
    }
  },
  {deep:true,immediate:true}
)


const emits=defineEmits(['update:modelValue'])


const handleClose = () => {
  emits('update:modelValue',false)
}

const tableData=ref({})




</script>

<style scoped>

</style>