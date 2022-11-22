<template>
  <el-dialog :model-value="dialogVisible" :title="dialogTitle" width="30%" @close="handleClose">
    <el-form ref="formRef" :model="form" label-width="100px" :rules="rules">

      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" placeholder="Select" :model-value="form.type" @change="typeChange">
          <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="类型" prop="type">
        <el-input v-model="form.type"></el-input>
      </el-form-item> -->
      <el-form-item v-if="form.type == 0" label="员工号" prop="employeeId">
        <el-input v-model="form.employeeId"></el-input>
      </el-form-item>
      <el-form-item v-if="form.type == 1" label="URL地址" prop="url">
        <el-input v-model="form.url"></el-input>
      </el-form-item>
      <el-form-item v-if="form.type == 2" label="小程序页面" prop="appPage">
        <el-input v-model="form.appPage"></el-input>
      </el-form-item>
      <el-form-item label="序号" prop="sort">
        <el-input v-model="form.sort"></el-input>
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
import { getServerUrl, filePathHandler } from "@/config/sys";
import { ElMessage, ElMessageBox } from "element-plus";

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

const typeOptions = ref([
  {
    value: '0',
    label: '员工',
  },
  {
    value: '1',
    label: '链接',
    disabled: true,
  },
  {
    value: '2',
    label: '小程序页面',
    disabled: true,
  }
])

const initFormData = async (id) => {
  if (id != -1) {
    const res = await axios.get("/admin/slideshow/" + id);
    form.value = res.data.notice;
    console.log("form", form)
  }

}

const form = ref({
  id: -1,
  type: -1,
  imgSrc: '',
  appPage: '',
  sort: 0,
  employeeId: -1,
  url: ""
})

watch(
  () => props.id,
  () => {
    let id = props.id
    console.log("iddddddddddddd", id)
    form.value.id = id;

    if (id != -1) {
      // 请求后端 获取数据
      initFormData(id)
    }
    else {
      form.value = {
        id: -1,
        type: -1,
        imgSrc: '',
        appPage: '',
        sort: 0,
        employeeId: -1,
        url: ""
      }
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
        let result = await axios.post("admin/slideshow/save", form.value);
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
const typeChange = () => {
  // console.log("ss")
}


</script>

<style scoped>

</style>