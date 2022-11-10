<template>

  <el-dialog
    :model-value="productSwiperImageDialogVisible"
    title="tu'p"
    width="30%"
    @close="handleClose"

  >

    <el-form ref="formRef" :model="form" label-width="100px" style="text-align: center" :rules="rules">

      <el-form-item label="排列序号" prop="sort">
        <el-input v-model="form.sort" style="width: 100px"/>
      </el-form-item>

      <el-upload
        :headers="headers"
        class="avatar-uploader"
        :action="getServerUrl()+'/publish/uploadPublishImage'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      > <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
      </el-upload>

      <el-form-item>
        <el-button type="primary"  @click="handleConfirm()">添加</el-button>
      </el-form-item>



    </el-form>


    <el-table :data="tableData" stripe style="width: 100%">

      <el-table-column type="index" width="50" />

      <el-table-column prop="image" label="轮播图片"  >
        <template v-slot="scope">
          <img :src="getServerUrl()+scope.row.src" width="80" height="80"/>
        </template>
      </el-table-column>

      <el-table-column prop="sort" label="排列序号" width="100" />

      <el-table-column prop="action" label="操作" width="100" >
        <template v-slot="scope">
          <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button>
        </template>

      </el-table-column>

    </el-table>


  </el-dialog>

</template>

<script setup>
import { defineEmits,ref ,defineProps,watch} from 'vue'
import axios from "@/util/axios";
import { ElMessage, ElMessageBox } from "element-plus";
import {getServerUrl} from "@/config/sys";
import { Delete,Plus } from '@element-plus/icons-vue'

const emits=defineEmits(['update:modelValue'])

const rules=ref({
  sort:[
    { required: true, message: '请输入排列序号!' },
    { type: 'number', message: '排列序号是数值类型！',transform: (value) => Number(value) },
  ]
})

const handleClose = () => {
  emits('update:modelValue',false)
}

const formRef=ref(null)

const imageUrl=ref("")

const headers=ref({
  token:window.sessionStorage.getItem('token')
})



const form=ref({
  pid:-1,
  sort:0,
  src:''
})

const props=defineProps({
  pid:{
    type:Number,
    default:-1,
    required:true
  }
})

const initProductSwiperImageList=async()=>{
  console.log('xxx')
  const res=await axios.get("publish/listImg/"+form.value.pid);
  tableData.value=res.data.productSwiperImageList;
}

watch(
  ()=>props.pid,
  ()=>{
    console.log("id=="+props.pid);
    let pid=props.pid;
    form.value.pid=pid;
    initProductSwiperImageList();
  },
  {deep:true,immediate:true}
)

const tableData=ref([
])


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
  form.value.src=res.data.title;
}


const handleConfirm = () => {
  if(imageUrl.value==""){
    ElMessage.error("请上传图片！");
    return;
  }
  formRef.value.validate(async (valid) => {
    if (valid) {
      console.log("xxx")
      form.value.src="/image/productIntroImgs/"+form.value.src;
      let result=await axios.post("publish/addImg",form.value);
      let data=result.data;
      if(data.code==0){
        ElMessage.success("执行成功");
        imageUrl.value=""
        formRef.value.resetFields();
        await initProductSwiperImageList();

      }else{
        ElMessage.error(data.msg);
      }
    } else {
      return false
    }
  })
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
      let res=await axios.get("publish/deleteImg/"+id);
      if(res.data.code==0){
        ElMessage({
          type: 'success',
          message: '删除成功！',
        });
        initProductSwiperImageList();
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