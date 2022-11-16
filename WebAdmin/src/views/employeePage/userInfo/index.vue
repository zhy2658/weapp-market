<template>
    <div class="home">
        <!-- 员工信息 -->
        <!-- <h2 style="color:red">您当前未设置密码，请尽快设置</h2> -->
        <!-- <h1>昵称：{{ userInfo }}</h1> {{ status }} -->
        <div class="container">
            <!-- {{userInfo.nickName}} -->
            <div class="fileInputContainer">
                <img class="avatarImg" :src="filePathHandler(userInfo.avatarUrl)" @click="handleChangeImage" alt="">
            </div>
            <div class="Content-Main">
                <!-- <form action="" method="post" class="form-userInfo"> -->
                <label>
                    <el-button v-if="!isEdit" type="default" class="editbutton" @click="eidtForm()">编辑</el-button>
                    <el-button v-else type="danger" class="editbutton" @click="eidtForm()">取消编辑</el-button>
                </label>
                <h1>员工信息</h1>

                <!-- <span class="text1">请在文本框中完善您的个人信息：</span> -->


                <label>
                    <span>昵称:</span>
                    <input type="text" name="name" v-model="userInfo.nickName" placeholder="Your Full Name"
                        :disabled="!isEdit" />
                </label>
                <label class="Main-sex">
                    <span>性别:</span>
                    <!-- <input type="checkbox" class="man" >男
                        <input type="checkbox" class="women">女 -->
                    <el-select v-if="isEdit" v-model="userInfo.sex">
                        <el-option label="女" value="1" :key="1"></el-option>
                        <el-option label="男" value="2" :key="2"> </el-option>
                    </el-select>
                    <input v-else type="text" :value="sexOption(userInfo.sex)" disabled />
                    <!-- <span>{{sexOption(userInfo.sex)}}</span> -->
                    <!-- {{userInfo.sex}} -->
                </label>
                <label>
                    <span>微信号 :</span>
                    <input type="email" name="email" placeholder="" v-model="userInfo.wxid" :disabled="!isEdit">
                </label>
                <label>
                    <span>手机号 :</span>
                    <input v-if="userInfo.tel" type="email" name="email" placeholder="" v-model="userInfo.tel"
                        :disabled="!isEdit">
                    <input v-else style="color:red" type="email" name="email" placeholder="" value="必填，用于接收下单信息"
                        disabled="" />
                </label>
                <label>
                    <span>年龄:</span>
                    <input type="text" name="phone" placeholder="Please input 11 number" v-model="userInfo.age"
                        :disabled="!isEdit">
                </label>
                <label>
                    <span>标签:</span>
                    <input type="text" name="phone" placeholder="Please input 11 number" v-model="userInfo.tags"
                        :disabled="!isEdit">
                </label>
                <label>
                    <span>简述:</span>
                    <textarea id="message" name="message" placeholder="Your message to us"
                        v-model="detailObj.description" :disabled="!isEdit"></textarea>
                </label>

                <div>
                    <span style="display:block;font-weight: 600;">详细介绍:</span>
                </div>
                <br>
                <div>
                    <QuillEditor v-if="isEdit" v-model:content="detailObj.productIntroImgs" contentType="html"
                        toolbar="full" theme="snow"  />
                    <div v-else class="productIntroImgs"  >
                        <div ref="reportHTML" v-html="detailObj.productIntroImgs" class="web-con"></div>
                        <!-- {{  }} -->
                    </div>
                </div>


                <div class="demo-image__preview">

                    <el-image v-for="(swiperImage, index) in detailObj.productSwiperImageList"
                        style="width: 100px; height: 100px"
                        :src="filePathHandler('/image/productSwiperImgs/' + swiperImage.image)"
                        :preview-src-list="srcList">
                    </el-image>
                    <br>
                    最多上传四张照片
                    <el-button type="default" @click="handleChangeProductSwiperImage">上传照片</el-button>
                </div>


                <label>
                    <el-button type="primary" class="submit_btn" @click="sumbitForm()">保存</el-button>
                </label>
                <!-- </form> -->
            </div>
        </div>

    </div>
    <ProductSwiperImageDialog :productId="detailObj.id" v-model="productSwiperImageDialogVisible">
    </ProductSwiperImageDialog>
    <ImageDialog v-model="imageDialogVisible" :id="id" :uid="uid" @initUserList="initUserList" />


</template>
<script setup>

import { Search, Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from '@/util/axios'
import { getServerUrl, filePathHandler } from "@/config/sys";
import { showOption, adminOption, sexOption } from "@/config/option.js";

import { ElMessageBox, ElMessage } from 'element-plus'

import ProductSwiperImageDialog from './components/productSwiperImageDialog'
import ImageDialog from './components/imageDialog'


const srcList = ref([
    'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
]);

const productSwiperImageDialogVisible = ref(false)
const imageDialogVisible = ref(false)

const id = ref(-1)
const uid = ref(-1)

const isEdit = ref(false)
const userInfo = ref({});
const detailObj = ref({});

const initUserList = async () => {
    let sessionUserInfo = JSON.parse(sessionStorage.getItem("userInfo"));
    console.log(userInfo);
    const res = await axios.post("product/detailByopenId?openid=" + sessionUserInfo.openid);
    // console.log(res.data.userInfo)
    // console.log(res.data.message)
    userInfo.value = res.data.userInfo;
    detailObj.value = res.data.message;
    // console.log(userInfo.value);
}
initUserList();

const sumbitForm = async () => {
    let formObj = userInfo.value;
    // console.log(formObj)
    for (let key in formObj) {
        if (!formObj[key]) continue;

        if (formObj[key].trim) {
            formObj[key] = formObj[key].trim()
        }

        if (key == "nickName" && (formObj[key].length > 4 || formObj[key].length == 0)) {
            ElMessage.error("昵称长度为1-4位");
            return;
        }
        if (key == "tags" && formObj[key].split(",").length > 3) {
            ElMessage.error("最多只有三个标签");
            return;
        }

    }
    console.log("here")
    var productObj = detailObj.value;
    // if (productObj.productSwiperImageList > 4) {
    //     ElMessage.error("上传的图片不能超过4张");
    //     return false;
    // }
    // if (productObj.productSwiperImageList.length > 0) {
    //     productObj.swiper = true;
    //     productObj.swiperPic = productObj.productSwiperImageList[0].image;
    // }

    const res = await axios.post("users/update_user", { userInfo: formObj, product: productObj });
    console.log(res.data)
    if (res.data.code == 0) {
        userInfo.value = formObj
        sessionStorage.setItem('userInfo', JSON.stringify(formObj))
        isEdit.value = false

        ElMessage.success("操作成功！");
    }

}

const handleChangeImage = (row) => {
    id.value = userInfo.value.id;
    uid.value = userInfo.value.id;
    imageDialogVisible.value = true;
}


const handleChangeProductSwiperImage = (row) => {
    // id.value=row.id;
    // uid.value=row.userInfo.id;
    productSwiperImageDialogVisible.value = true;
}


const eidtForm = () => {
    if (isEdit.value) {
        isEdit.value = false
    }
    else {
        isEdit.value = true;
    }
}



</script>

<style lang="scss" scoped>
.container {
    display: flex;
    justify-content: center;
}

.Content-Main {
    position: relative;
    flex: 7;
    max-width: 100%;
    margin: auto;
    border: none;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    font: 12px "Helvetica Neue", Helvetica, Arial, sans-serif;
    // text-shadow: 1px 1px 1px #fff;
    color: #454545;
    background: #fff;
}

.Content-Main h1 {
    padding: 8px 0px 40px 10px;
    display: block;
    border-bottom: 1px solid #444;
}

.text1 {
    margin-left: 3px;
}

.Content-Main label {
    margin: 0px 0px 5px;
    display: block;
}

.fileInputContainer {
    // flex: 1;
    height: 99px;
    width: 99px;
    margin: 20px 20px 20px 20px;
    border: none;
    overflow: hidden;
    position: relative;
    // float: left;
    background-color: #efefef;
}

.avatarImg {
    cursor: pointer;
    width: 99px;
    height: 99px;
    border: 2px solid #efefef;
}

.fileInput {
    height: 106px;
    border: none;
    font-size: 300px;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer;
    position: absolute;
}


.Content-Main label>span {
    width: 20%;
    float: left;
    text-align: right;
    padding-right: 10px;
    margin-top: 10px;
    font-weight: bold;
}

.Main-sex {
    padding-right: 13px;
    padding-bottom: 13px;
    font-weight: bold;
    line-height: 4px;
}

.Main-sex input[type=checkbox] {
    margin-top: 6px;
    vertical-align: middle;
}

.Content-Main input[type="text"],
.Content-Main input[type="email"],
.Content-Main textarea {
    height: 25px;
    width: 70%;
    line-height: 15px;
    padding: 5px 0px 5px 5px;
    margin-bottom: 16px;
    margin-right: 6px;
    margin-top: 2px;
    border: none;
    border-radius: 2px;
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    outline: 0 none;
    background: #DFDFDF;
    color: #525252;
    // border-bottom: 1px solid #efefef;
}

.editbutton {
    position: absolute;
    right: 180px;
}

.Content-Main textarea {
    height: 100px;
    width: 70%;
    padding: 5px 0px 0px 5px;
}

.submit_btn {
    margin-top: 30px;
}

.productIntroImgs {
    width: 90%;
    // width: 70%;
    // background-color: red;
    // width: 800px;
    // height: 300px;
    margin-bottom: 30px;
}

.Content-Main .button {
    padding: 8px 24px 8px 24px;
    margin-bottom: 8px;
    border: none;
    border-radius: 4px;
    -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
    font-weight: bold;
    text-shadow: 1px 1px 1px #FFE477;
    box-shadow: 1px 1px 1px #3D3D3D;
    -moz-box-shadow: 1px 1px 1px #3D3D3D;
    -webkit-box-shadow: 1px 1px 1px #3D3D3D;
    color: #585858;
    background: #f6ff0a;
}

.Content-Main .button:hover {
    color: #333;
    background-color: #EBEBEB;
}
</style>
