// pages/messageList/index.js

import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        baseUrl: "",
        msgSessionList: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        const baseUrl = getBaseUrl();
        this.setData({
            baseUrl: baseUrl
        })
        this.getMsgList();

    },
    async getMsgList() {
        const result = await requestUtil({
            url: "/chat/getAllMsgSession",

        });
        this.setData({
            msgSessionList: result
        });
        console.log(this.data.msgSessionList)


    },
    // ListTouch触摸开始
    ListTouchStart(e) {
        this.setData({
            ListTouchStart: e.touches[0].pageX
        })
    },
    // ListTouch计算方向
    ListTouchMove(e) {
        this.setData({
            ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > 0 ? 'right' : 'left'
        })
    },
    // ListTouch计算滚动
    ListTouchEnd(e) {
        if (this.data.ListTouchDirection == 'left') {
            this.setData({
                modalName: e.currentTarget.dataset.target
            })
        } else {
            this.setData({
                modalName: null
            })
        }
        this.setData({
            ListTouchDirection: null
        })
    },
    openchatroom(e){
        // console.log(e.currentTarget.dataset.openid)
        wx.navigateTo({
            url: '/pages/chatroom/index?openid='+e.currentTarget.dataset.openid
            // url: 'page/chatroom/index'
          });
        //   wx.navigateTo({
        //     url: '/pages/chatroom/index',
        //   })
    },
    onPullDownRefresh: function () {
        // console.log("---")
        this.onRefresh();
    },
    onRefresh: function () {
        console.log("---")
        //导航条加载动画
        wx.showNavigationBarLoading()
        //loading 提示框
        wx.showLoading({
            title: 'Loading...',
        })
        this.getMsgList();
        // this.getRandom();
        console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000)
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },


    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
  

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})