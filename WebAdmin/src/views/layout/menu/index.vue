<template>
  <el-menu
    active-text-color="#ffd04b"
    background-color="#2d3a4b"
    class="el-menu-vertical-demo"
    default-active="home"
    text-color="#fff"
    router
  >
    <el-menu-item index="home">
      <el-icon><home-filled /></el-icon>
      <span>首页</span>
    </el-menu-item>

    <el-menu-item index="notice">
      <el-icon><user /></el-icon>
      <span>通知管理</span>
    </el-menu-item>
    
    <el-menu-item index="slideshow">
      <el-icon><user /></el-icon>
      <span>主页轮播</span>
    </el-menu-item>


    <el-menu-item index="user">
      <el-icon><user /></el-icon>
      <span>员工管理</span>
    </el-menu-item>

    <el-sub-menu index="3">
      <template #title >
        <el-icon><management /></el-icon>
        <span>用户类别管理</span>
      </template>
      <el-menu-item index="bigType">
        <el-icon><management /></el-icon>
        <span>用户大类管理</span>
      </el-menu-item>
      <el-menu-item index="smallType">
        <el-icon><management /></el-icon>
        <span>用户小类管理</span>
      </el-menu-item>
    </el-sub-menu>

    <el-menu-item index="product">
      <el-icon><tickets /></el-icon>
      <span>用户管理</span>
    </el-menu-item>

    <el-menu-item index="order">
      <el-icon><tickets /></el-icon>
      <span>订单查看</span>
    </el-menu-item>
    <el-menu-item index="topupRecord">
      <el-icon><tickets /></el-icon>
      <span>充值纪录</span>
    </el-menu-item>
    <el-menu-item index="publish">
      <el-icon><tickets /></el-icon>
      <span>万能墙管理</span>
    </el-menu-item>
    <el-menu-item index="serviceOption">
      <el-icon><tickets /></el-icon>
      <span>服务选项</span>
    </el-menu-item>
    <el-menu-item index="statistics">
      <el-icon><tickets /></el-icon>
      <span>统计</span>
    </el-menu-item>
    
    <el-sub-menu index="11">
      <template #title >
        <el-icon><management /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="modifyPassword">
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