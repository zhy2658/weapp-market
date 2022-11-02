<template>
  <el-dialog :model-value="dialogVisible" :title="dialogTitle" width="30%" @close="handleClose">
    <el-form ref="formRef" :model="form" label-width="100px" :rules="rules">
      <el-form-item label="项目名" prop="itemName">
        <el-input v-model="form.itemName"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model="form.price"></el-input>
      </el-form-item>
      <el-form-item label="小时数" prop="itemHours">
        <el-input v-model="form.itemHours"></el-input>
      </el-form-item>
      <el-form-item label="等级" prop="grade">
        <el-input v-model="form.grade" ></el-input>
      </el-form-item>
      <!-- <el-form-item label="内容" prop="message">
        <el-input v-model="form.message" :rows="4" type="textarea" />
      </el-form-item> -->
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { defineEmits, ref, defineProps, watch } from 'vue'
import axios from "@/util/axios";
import { ElMessage } from "element-plus";

const props = defineProps({
  dialogTitle: {
    type: String,
    default: '',
    required: true
  },
  id: {
    type: Number,
    default: -1,
    required: true
  },
  status: {
    type: Number,
    default: -1,
    required: true
  }
})

const initFormData = async (id) => {
  if (id != -1) {
    const res = await axios.get("/admin/playitem/" + id);
    form.value = res.data.payitem;
    console.log("form", form)
  }

}
 
const form = ref({
  id: -1,
  price: '',
  itemName: '',
  itemHours: '',
  grade: props.status
})

watch(
  () => props.id,
  () => {
    console.log("id=" + props.id);
    let id = props.id

    form.value.id = id;
    console.log("status=" + props.status);
    form.value.grade = props.status
    if (id != -1) {
      // 请求后端 获取数据
      initFormData(id)
    }
  },
  { deep: true, immediate: true }
  
)


const emits = defineEmits(['update:modelValue', 'initUserList'])

const formRef = ref(null)

const handleClose = () => {
  emits('update:modelValue', false)
  formRef.value.resetFields();
}


const rules = ref({
  name: [
    {
      required: true,
      message: '请输入标题',
    }
  ],
  remark: [
    {
      required: true,
      message: '请输入内容',
    },
  ]
})

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        console.log(form.value)
        let result = await axios.post("admin/playitem/save", form.value);
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
</script>

<style scoped>

</style>