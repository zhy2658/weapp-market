<template>
  <el-container class="app-wrapper">
    <el-aside width="200px" class="sidebar-container"><Menu/></el-aside>
    <el-container>
      <el-container class="container">
        <el-header>
          <div class="navbar">
            <Breadcrumb/>
            <div class="navbar-right">
              <Avatar/>
            </div>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
        <el-footer>
          <Footer/>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script setup>
import Menu from '@/views/layout/menu'
import Breadcrumb from '@/views/layout/header/components/breadcrumb'
import Avatar from '@/views/layout/header/components/avatar'
import Footer from '@/views/layout/footer'
</script>

<style lang="scss" scoped>

.navbar {
  width: 100%;
  height: 60px;
  overflow: hidden;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  position: relative;
  .navbar-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    ::v-deep .navbar-item {
      display: inline-block;
      margin-left: 18px;
      font-size: 22px;
      color: #5a5e66;
      box-sizing: border-box;
      cursor: pointer;
    }
  }
}

.app-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
}
.sidebar-container{
  background-color: #2d3a4b;
  height: 100%;
}
.container {
  width: calc(100% - 200px);
  height: 100%;

  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  transition: all 0.28s;

}
::v-deep .el-header {
  padding: 0;
}
</style>