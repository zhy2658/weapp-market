<template>
  <div class="home">
    你好！晚点的心声的店员 <br><br>
    <!-- <h2 style="color:red" >您当前未设置密码，请尽快设置</h2> -->
  </div>

</template>

<script >
import { ref } from 'vue'

export default {
  name: "index"

};

const userInfo = ref({});

const initUserList = async () => {
    let sessionUserInfo = JSON.parse(sessionStorage.getItem("userInfo"));
    userInfo.value=sessionUserInfo;
}
// initUserList();


</script>

<style lang="scss" scoped>
.home{
  padding: 40px;
  font-size: 30px;
  font-weight: bold;
}
</style>