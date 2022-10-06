// pages/setting/index.js
import { getBaseUrl, requestUtil, getLogin, getUserProfile, requestPay } from "../../utils/requestUtil.js";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        BaseUrl:""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        const BaseUrl=getBaseUrl();
        this.setData({BaseUrl})
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },
    logout() {
        wx.showModal({
            title: '提示',
            content: '确定注销',
            success(res) {
                if (res.confirm) {
                    wx.clearStorage({
                        success: (res) => {
                            wx.navigateBack({
                                delta: 1
                            })
                        },
                    })
                }
            }

        });

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
    onPullDownRefresh() {

    },

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