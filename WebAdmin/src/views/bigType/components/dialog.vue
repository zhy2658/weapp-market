<template>
  <el-dialog
    :model-value="dialogVisible"
    :title="dialogTitle"
    width="30%"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" label-width="100px" :rules="rules">
      <el-form-item label="大类名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="大类描述" prop="remark">
        <el-input
          v-model="form.remark"
          :rows="4"
          type="textarea"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { defineEmits,ref ,defineProps,watch} from 'vue'
import axios from "@/util/axios";
import { ElMessage } from "element-plus";

const props=defineProps({
  dialogTitle:{
    type:String,
    default:'',
    required:true
  },
  id:{
    type:Number,
    default:-1,
    required:true
  }
})

const initFormData=async(id)=>{
  const res=await axios.get("admin/bigType/"+id);
  form.value=res.data.bigType;
}

const form=ref({
  id:-1,
  name:'',
  remark:''
})

watch(
  ()=>props.id,
  ()=>{
    console.log("id="+props.id);
    let id=props.id

    form.value.id=id;

    if(id!=-1){
      // 请求后端 获取数据
      initFormData(id)
    }
  },
  {deep:true,immediate:true}
)


const emits=defineEmits(['update:modelValue','initBigTypeList'])

const formRef=ref(null)

const handleClose = () => {
  emits('update:modelValue',false)
  formRef.value.resetFields();
}


const rules=ref({
  name:[
    {
      required: true,
      message: '请输入商品大类名称！',
    }
  ],
  remark:[
    {
      required: true,
      message: '请输入商品大类描述！',
    },
  ]
})

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try{

        let result=await axios.post("admin/bigType/save",form.value);
        let data=result.data;
        if(data.code==0){
          ElMessage.success("执行成功");
          formRef.value.resetFields();
          emits("initBigTypeList")
          handleClose();
        }else{
          ElMessage.error(data.msg);
        }
      }catch(err){
        console.log("error:"+err)
        ElMessage.error('系统运行出错，请联系管理员');
      }
    } else {
      return false
    }
  })
}
</script>

<style scoped>

</style>