<template>

    <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar shape="square" :size="40" :src="filePathHandler(userInfo.avatarUrl)"></el-avatar>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="logout">安全退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>


</template>

<script setup>
import {ref} from 'vue'
import {useStore} from 'vuex'
import { getServerUrl, filePathHandler } from "@/config/sys";
const store=useStore();
const squareUrl=ref("https://qq.yh31.com/tp/zjbq/202005071006406675.gif")

const logout=()=>{
  store.dispatch('logout')
}


const userInfo = ref({});

const initUserList = async () => {
    let sessionUserInfo = JSON.parse(sessionStorage.getItem("userInfo"));
    userInfo.value=sessionUserInfo;
}
initUserList();

</script>

<style scoped>

</style>