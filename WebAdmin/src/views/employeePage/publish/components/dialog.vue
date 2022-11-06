<template>
  <el-dialog
    model-value="dialogVisible"
    :title="dialogTitle"
    width="50%"
    @close="handleClose"
  >
  <el-form ref="formRef" :model="form" label-width="100px" :rules="rules">
    <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" style="width: 400px"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="form.content" :rows="4" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>

  <!-- <div>{{tableData.content}}</div> -->
  <div style="display:flex;flex-wrap: wrap;">
    <div v-for="(item,index) in form.imgList">
      <img :src="filePathHandler(item.src)" style="width:100px;"/>
     
    </div>
  </div>
  </el-dialog>
</template>

<script setup>
import { defineEmits,ref ,defineProps,watch} from 'vue'
import axios from "@/util/axios";
import { ElMessage } from "element-plus";
import {getServerUrl,filePathHandler} from "@/config/sys";

const props=defineProps({
  dialogTitle: {
    type: String,
    default: '',
    required: true
  },
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

const formRef = ref(null)

const form = ref({
  id: -1,
  title: '',
  content: '',
  // address: ''
})


const initOrderDetailData=async(id)=>{
  if(id !=-1){
    const res=await axios.get("publish/selectone?pid="+id);
    form.value=res.data.publish;
    console.log(form.value)
  }
 
  // tableData.value = props.currentRow;
  // console.log(" tableData.value", tableData.value)
  
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
  formRef.value.resetFields();
}
const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        console.log(form.value)
        let result = await axios.post("publish/add_publish",
             {
              publish: form.value,
              publishImgs:[]
             }
         );
        let data = result.data;
        if (data.code == 0) {
          ElMessage.success("执行成功");
          formRef.value.resetFields();
          emits("initOrderList")
          handleClose();
        } else {
          ElMessage.error(data.msg);
        }
      } catch (err) {
        console.log("error:" + err)
        ElMessage.error('系统运行出错，请联系管理员');
      }
    } else {
      return false
    }
  })
}

const tableData=ref({})




</script>

<style scoped>

</style>