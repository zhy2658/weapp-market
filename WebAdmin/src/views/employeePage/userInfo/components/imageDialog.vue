<template>

  <el-dialog
    :model-value="imageDialogVisible"
    title="员工图片更换"
    width="30%"
    @close="handleClose"
    center
  >
    <el-form ref="formRef" :model="form" label-width="100px" style="text-align: center">
      <el-upload
        :headers="headers"
        class="avatar-uploader"
        :action="getServerUrl()+'/admin/product/uploadImage'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      > <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
      </el-upload>


    </el-form>

    <template #footer>

      <span class="dialog-footer">

        <el-button type="primary" @click="handleConfirm"
        >确认更换</el-button
        >
      </span>

    </template>

  </el-dialog>

</template>

<script setup>
import { defineEmits,ref ,defineProps,watch} from 'vue'
import axios from "@/util/axios";
import { ElMessage  } from "element-plus";
import {getServerUrl} from "@/config/sys";
import { Plus } from '@element-plus/icons-vue'

const emits=defineEmits(['update:modelValue','initProductList'])



const props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  },
  uid:{
    type:Number,
    default:-1,
    required:true
  }
})

const form=ref({
  id:-1,
  userInfo:{id:"",avatarUrl:''}
})

const imageUrl=ref("")

const headers=ref({
  token:window.sessionStorage.getItem('token')
})


const imageSrc=ref("")



watch(
  ()=>props.id,
  ()=>{
    let id=props.id;
    form.value.id=id;
    form.value.userInfo.id=props.uid;
    // /image/product/20220929081909000000725.jpg
    imageSrc.value=getServerUrl()+'/image/product/'+form.value.image;
    



  },
  {deep:true,immediate:true}
)

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    ElMessage.error('图片必须是jpg格式')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2M!')
  }
  return isJPG && isLt2M
}

const handleAvatarSuccess = (res) => {
 console.log(res.data)
  imageUrl.value=getServerUrl()+res.data.src;
  form.value.userInfo.avatarUrl=res.data.title;
}


const handleConfirm = async() => {
  console.log(form.value)
  form.value.userInfo.avatarUrl = '/image/product/'+form.value.userInfo.avatarUrl;
  let result=await axios.post("users/update_user",form.value);
  let data=result.data;
  if(data.code==0){
    ElMessage.success("执行成功");
    imageUrl.value=""
   emits("initUserList")
   handleClose();
  }else{
    ElMessage.error(data.msg);
  }
}

const handleClose = () => {
  emits('update:modelValue',false)
}

</script>

<style >

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>