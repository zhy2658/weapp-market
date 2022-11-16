<template>
  <el-dialog :model-value="dialogVisible" :title="dialogTitle" width="30%" @close="handleClose">
    <el-form ref="formRef" :model="form" label-width="100px" :rules="rules">
      <el-form-item label="员工等级" prop="employee_grade">
        <el-select v-model="form.employee_grade" class="m-2" placeholder="Select">
          <el-option
            v-for="(item,index) in memberGradeOption()"
            :key="index"
            :label="item"
            :value="index"
          />
        </el-select>
        <!-- <el-input v-model="form.employee_grade"></el-input> -->
      </el-form-item>
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
import { showOption, adminOption,memberGradeOption } from "@/config/option.js";

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
  }
})

const initFormData = async (id) => {
  if (id != -1) {
    const res = await axios.get("/product/getUser/" + id);
    form.value = res.data.userInfo;
    console.log("form", form)
  }

}

const form = ref({
  id: -1,
  title: '',
  message: '',
  n_time: '',
  top: -1
})

watch(
  () => props.id,
  () => {
    console.log("id=" + props.id);
    let id = props.id

    form.value.id = id;

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
        let result = await axios.post("admin/user/save", form.value);
        let data = result.data;
        if (data.code == 0) {
          ElMessage.success("执行成功");
          formRef.value.resetFields();
          emits("initUserList")
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