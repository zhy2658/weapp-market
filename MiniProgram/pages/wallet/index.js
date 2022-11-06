// pages/wallet/index.js
import { getBaseUrl, requestUtil, getLogin, getUserProfile, requestPay } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';


Page({

    /**
     * 页面的初始数据
     */
    data: {
        purchaseOption: [
            {
                totalPrice: 0.01,
                coinNum: 60
            },
            {
                totalPrice: 0.02,
                coinNum: 200
            }, {
                totalPrice: 50,
                coinNum: 500
            }
            , {
                totalPrice: 100,
                coinNum: 2000
            }

        ],
        purchaseActive: 0,
        userInfo:{}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getDetail();
    },
    async getDetail(){
        let userInfo = wx.getStorageSync('userInfo');
        const result = await requestUtil({ url: "/product/detailByopenId", data:{openid:userInfo.openid} });
        // let audioPlayObj = this.data.audioPlayObj;
        // audioPlayObj.audioSrc = this.data.BaseUrl+ result.message.audio;
        userInfo=result.userInfo;
        userInfo.coin = userInfo.coin.toFixed(0);
        this.setData({
            userInfo: result.userInfo,
            // productObj: result.message,
            // audioPlayObj
        })
        wx.setStorageSync('userInfo', userInfo)
    },
    changePurchaseActive(e) {
        let activeIndex = e.currentTarget.dataset.index;
        this.setData({
            purchaseActive: activeIndex
        })
    },

    cancelPurchaseActive() {
        this.setData({
            purchaseActive: -1
        })
    },
    async pay() {
        const purchaseActive = this.data.purchaseActive;
        if (purchaseActive == -1) return;
        let topupRecord = {
            totalPrice: this.data.purchaseOption[purchaseActive].totalPrice,
            coinNum: this.data.purchaseOption[purchaseActive].coinNum
        };
        try {

            const res = await requestUtil({ url: "/topupRecord/create", method: "POST", data: topupRecord });
            console.log(res.topupNo);
            let topupNo = res.topupNo;
            console.log("topupNo:" + topupNo);

            const preparePayRes = await requestUtil({ url: "/topupRecord/preparePay", method: "POST", data: topupNo });
            console.log("preparePayRes:" + preparePayRes)
            let payRes = await requestPay(preparePayRes);

            if (payRes.errMsg.indexOf("ok") != 0) {
                const preparePayRes = await requestUtil({
                    url: "/topupRecord/setOrderStatus", method: "POST",
                    data: {
                        topupNo: topupNo
                    }
                });
            }

            console.log(payRes)
            wx.showToast({
                title: '支付成功',
                icon: 'none'
            });
            this. getDetail();


        } catch (error) {
            console.log(error);
            wx.showToast({
                title: '支付失败',
                icon: 'none'
            })

        }
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