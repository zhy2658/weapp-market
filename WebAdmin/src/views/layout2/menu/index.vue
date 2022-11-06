<template>
  <el-menu
    active-text-color="#ffd04b"
    background-color="#2d3a4b"
    class="el-menu-vertical-demo"
    default-active="home"
    text-color="#fff"
    router
  >
    <el-menu-item index="employee_home">
      <el-icon><home-filled /></el-icon>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="employee_userInfo">
      <el-icon><home-filled /></el-icon>
      <span>主页信息</span>
    </el-menu-item>

    <el-menu-item index="employee_order">
      <el-icon><tickets /></el-icon>
      <span>我的订单</span>
    </el-menu-item>
    <el-menu-item index="employee_publish">
      <el-icon><tickets /></el-icon>
      <span>我的帖子</span>
    </el-menu-item>
    
    <el-sub-menu index="11">
      <template #title >
        <el-icon><management /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="employee_modifyPassword">
        <el-icon><edit /></el-icon>
        <span>修改密码</span>
      </el-menu-item>
      <el-menu-item >
        <el-icon @click="logout"><switch-button /></el-icon>
        <span @click="logout">安全退出</span>
      </el-menu-item>
    </el-sub-menu>


  </el-menu>
</template>

<script setup>
import {HomeFilled,User,Tickets,Goods,DocumentAdd,Management,Setting,Edit,SwitchButton} from '@element-plus/icons-vue'

import {useStore} from 'vuex'
const store=useStore();


const logout=()=>{
  store.dispatch('logout')
}

</script>

<style scoped>

</style>