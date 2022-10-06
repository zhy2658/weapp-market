// 导入request请求工具方法
import { getBaseUrl, requestUtil, getLogin, getUserProfile, requestPay } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';


Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        BaseUrl: "",
        token: "",
        isShowLogin:true
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })
    },
    onShow: function () {
        const token = wx.getStorageSync('token');
        if (token) {
            // console.log("onShow")
            const userInfo = wx.getStorageSync('userInfo');
            this.setData({ userInfo, token , isShowLogin:false});
        }
        else{
            this.setData({ userInfo:{}, token:"" , isShowLogin:true});
            //  this.loginUser();
        }
    },
    loginUser() {
        Promise.all([getLogin(), getUserProfile()]).then((res) => {
            console.log(res)
            let loginParam = {
                code: res[0].code,
                nickName: res[1].userInfo.nickName,
                avatarUrl: res[1].userInfo.avatarUrl
            }
            console.log(loginParam);
            // 把用户信息放到缓存中
            // wx.setStorageSync('userInfo', res[1].userInfo);
            this.wxlogin(loginParam);
            // this.setData({ userInfo: res[1].userInfo ,isShowLogin:false});
        })

        // 判断缓存中是否有token
        // const token = wx.getStorageSync('token');
        // if (!token) {
        //     console.log("请求登录")
        //     // =======
        //     // =======
        //     // const {code}=await login();
        //     // wx.getUserProfile({
        //     //   desc: '获取用户信息',
        //     //   success: (res)=>{
        //     //     const {encryptedData,rawData,iv,signature}=res;

        //     //     console.log(code,encryptedData,rawData,iv,signature)
        //     //   },
        //     //   fail:()=>{
        //     //     console.log("登录失败")
        //     //   }
        //     // })

        //     wx.showModal({
        //         title: '友情提示',
        //         content: '微信授权登录后，才可进入个人中心',
        //         success: (res) => {
        //             console.log(getLogin(), getUserProfile())
        //             Promise.all([getLogin(), getUserProfile()]).then((res) => {
        //                 console.log(res)
        //                 let loginParam = {
        //                     code: res[0].code,
        //                     nickName: res[1].userInfo.nickName,
        //                     avatarUrl: res[1].userInfo.avatarUrl
        //                 }
        //                 console.log(loginParam);
        //                 // 把用户信息放到缓存中
        //                 wx.setStorageSync('userInfo', res[1].userInfo);
        //                 this.wxlogin(loginParam);
        //                 this.setData({ userInfo: res[1].userInfo });
        //             })
        //         }
        //     })
        // } else {
        //     console.log("token:" + token);
        // }
        // // console.log("onShow")
        // const userInfo = wx.getStorageSync('userInfo');
        // this.setData({ userInfo, token });

    },
    // 点击 编辑收货地址
    handleEditAddress() {
        console.log("编辑收货地址")
        wx.chooseAddress({

        });

    },

    /**
   * 请求后端获取用户token
   * @param {} loginParam 
   */
    async wxlogin(loginParam) {
        // 发送请求 获取用户的token
        const result = await requestUtil({ url: "/users/wxlogin", data: loginParam, method: "post" });
        let token = result.token;
        let userInfo = result.userInfo;
        if (result.code == 0) {
            // 存储token到缓存
            wx.setStorageSync('token', token);
            wx.setStorageSync('userInfo', userInfo);
            console.log(result.userInfo)
            this.setData({ token,userInfo: result.userInfo ,isShowLogin:false});
        }
    },
    async getDetail(){
        let userInfo = wx.getStorageSync('userInfo');
        const result = await requestUtil({ url: "/product/detailByopenId", data:{openid:userInfo.openid} });
        // let audioPlayObj = this.data.audioPlayObj;
        // audioPlayObj.audioSrc = this.data.BaseUrl+ result.message.audio;
        this.setData({
            userInfo: result.userInfo,
            // productObj: result.message,
            // audioPlayObj
        })
        wx.setStorageSync('userInfo', result.userInfo);
    },
    onPullDownRefresh: function () {
        this.onRefresh();
    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading()
        //loading 提示框
        wx.showLoading({
            title: '刷新中...',
        })  
        this.getDetail();
        console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000)
    }


    /**
     * 生命周期函数--监听页面显示
     */
   

})