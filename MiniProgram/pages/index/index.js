// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

let that;
Page({
    data: {
        // 轮播图数组
        swiperList: [],
        baseUrl: '',
        bigTypeList: [],
        bigTypeList_row1: [],
        bigTypeList_row2: [],
        hotProductList: [],

        // 通知面板
        isShowNotice:false,

        // 音乐播放器对象
        audioPlayObj: {
            isShowPlayArea: false,
            isPlayAudio: false,
            audioSrc: ''
        },
        // 通知
        notice:{}
    },
    onLoad: function () {
        that = this;

        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })

        this.getSwiperList();
        this.getBigTypeList();
        this.getHotProductList();
        this.getNewNotice();

    },
    async getNewNotice(){
        const result = await requestUtil({ url: "/notice/getNew" });
        this.setData({
            notice: result.notice
        })
        console.log(result)
    },
    // 获取轮播图数据
    async getSwiperList() {
        // requestUtil({ url: "/home/swiperdata" })
        //   .then(result => {
        //     this.setData({
        //       swiperList: result
        //     })
        //   })

        const result = await requestUtil({ url: "/product/findSwiper" });

        const baseUrl = getBaseUrl();
        this.setData({
            swiperList: result.message,
            baseUrl: baseUrl
        })
    },

    // 获取商品大类数据
    async getBigTypeList() {
        const result = await requestUtil({ url: "/bigType/findAll" });
        console.log(result)
        const bigTypeList_row1 = result.message.filter((item, index) => {
            return index < 5;
        })
        const bigTypeList_row2 = result.message.filter((item, index) => {
            return index >= 5;
        })
        this.setData({
            bigTypeList: result,
            bigTypeList_row1,
            // bigTypeList_row2
        })
    },

    // 获取热卖商品
    async getHotProductList() {
        const result = await requestUtil({ url: "/product/findHot" });
        console.log(result.users);
        for (let users of result.users) {
            users.isPlay = false;
            if(users.tags){
                users.tags=users.tags.split(",");
            }
            
        }
        this.setData({
            hotProductList: result.users
        })
        console.log(this.data.hotProductList)
    },
    // 大类点击事件处理 存储商品类别到全局数据
    handleTypeJump(event) {
        var index = event.currentTarget.dataset.index;
        console.log("index:" + index)
        const app = getApp();
        app.globalData.index = index;

        wx.switchTab({
            url: '/pages/category/index'
        })
    },
    userAudioPlay(e) {
        let index = e.target.dataset.index;
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        hotProductList[index].isPlay = true;
        this.setData({
            hotProductList,
            audioPlayObj: {
                audioSrc: that.data.baseUrl + hotProductList[index].audio,
                isShowPlayArea: false,
                isPlayAudio: true,
            }
        });
        // this.audioPlay();
    },
    userAudioPause(e) {
        let index = e.target.dataset.index;
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        let audioPlayObj = that.data.audioPlayObj;
        audioPlayObj.isShowPlayArea = false;
        audioPlayObj.isPlayAudio = false;
        this.setData({
            hotProductList,
            audioPlayObj: audioPlayObj
        });
        // this.audioPause();
    },
    // 播放器关闭时间
    audioClose() {
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        this.setData({
            hotProductList
        });
    },
    // 播放器暂停事件
    audioPause() {
        console.log("pause")
    },
    closeNotice(){
        this.setData({isShowNotice:false})
    },
    openNotice(){
        this.setData({isShowNotice:true})
    },


    //页面关闭时，停止播放
    onHide() {
        this.setData({
            isShowPlayArea: false,
            isPlayAudio: false
        });
    },
    onUnload() {
        this.setData({
            isShowPlayArea: false,
            isPlayAudio: false
        });
    },
    onPullDownRefresh: function () {
        // console.log("---")
        this.onRefresh();
    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading()
        //loading 提示框
        wx.showLoading({
            title: 'Loading...',
        })
        console.log("下拉index")
        this.getSwiperList();
        this.getBigTypeList();
        this.getHotProductList();
        // this.getRandom();
        // console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000)
    },




})