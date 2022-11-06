<template>
  <el-card>
    <el-form ref="formRef" :model="form" label-width="150px" :rules="rules">
      <el-form-item label="员工号："  prop="employee_id">
        <el-input v-model="form.employee_id"  disabled ></el-input>
      </el-form-item>
      <el-form-item v-if="form.password !=null" label="原密码：" prop="oldPassword" >
        <el-input v-model="form.oldPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码：" prop="newPassword">
        <el-input v-model="form.newPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认新密码：" prop="newPassword2">
        <el-input v-model="form.newPassword2" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>


import { ref } from "vue";
import axios from "@/util/axios";
import { ElMessage } from "element-plus";

const form=ref({
  id:"",
  employee_id:"",
  password:"",
  oldPassword:"",
  newPassword:"",
  newPassword2:""
})

const rules=ref({
  employee_id:[
    {
      required: true,
      message: '请输入用户名！',
    }
  ],
  oldPassword:[
    {
      required: true,
      message: '请输入密码！',
    },
  ],
  newPassword:[
    {
      required: true,
      message: '请输入新密码！',
    },
  ],
  newPassword2:[
    {
      required: true,
      message: '请输入确认新密码！',
    },
  ]
})

const formRef=ref(null)

const initFormData=()=>{
  let userInfoJson=window.sessionStorage.getItem("userInfo");
  form.value=JSON.parse(userInfoJson);
}

initFormData();

const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      if(form.value.oldPassword!=null&&form.value.oldPassword!==form.value.password){  // 判断原密码是否正确
        ElMessage.error("原密码错误！");
      }else if(form.value.newPassword!==form.value.newPassword2){  // 判断确认新密码是否正确
        ElMessage.error("确认新密码错误！");
      }else{ // 带上用户名和新密码 请求后端
        try{
          let result=await axios.post("employee/user/modifyPassword",form.value);
          let data=result.data;
          if(data.code==0){
            ElMessage.success("密码修改成功，重新登录后生效");
            formRef.value.resetFields();
          }else{
            ElMessage.error(data.msg);
          }
        }catch(err){
          console.log("error:"+err)
          ElMessage.error('系统运行出错，请联系管理员');
        }
      }
    } else {
      return false
    }
  })
}


</script>

<style lang="scss" scoped>


::v-deep .el-input__inner{
  width: 300px;
}
</style>