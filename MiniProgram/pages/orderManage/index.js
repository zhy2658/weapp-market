// pages/orderManage/index.js
// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

Page({

    /**
     * 页面的初始数据
     */
    data: {
        modalName: "",
        showItemIndex: 1,
        showItem: {
            // "0": "未支付",
            "1": "等待接单",
            "2":"正在进行", 
            "3":"待确认", 
            "4":"已完成",
            // "5":"请求退单中",
            // "6":"已退单"
            "9":"已拒接",
        },

        orderList: [],

        total: 0,
        totalPage: 0,
        QueryParams: {
            type: 1,
            page: 1, // 第几页
            pageSize: 6 // 每页记录数
        },
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        let that = this;

        this.getOrder(this.data.showItemIndex);
        setTimeout(function () {
            that.setData({
                loading: true
            })
        }, 500)
    },
    async getOrder(status, operation) {
        let QueryParams = this.data.QueryParams;
        QueryParams.status = status;
        this.setData({
            QueryParams
        })
        const res = await requestUtil({
            url: '/order/manage/getMyOrder',
            data: this.data.QueryParams
        });
        let orderList = [];
        // for( ){

        if (operation == "add") {
            orderList = this.data.orderList;
            orderList = orderList.concat(res.orderList)
        }
        else {
            orderList = res.orderList;
        }
        // }
        this.setData({
            orderList: orderList,
            total: res.total,
            totalPage: res.totalPage
        })
        console.log(res)
    },
    chooseShow(e) {
        let QueryParams = this.data.QueryParams;
        QueryParams.page = 1;
        this.getOrder(e.currentTarget.dataset.index)
        this.setData({
            showItemIndex: e.currentTarget.dataset.index,
            // total: 0,
            QueryParams
        })
    },
    showModal(e) {
        this.setData({
            modalName: e.currentTarget.dataset.target
        })
    },
    hideModal(e) {
        this.setData({
            modalName: null
        })
    },
    gridswitch: function (e) {
        // console.log(e.detail.value)
        // this.setData({
        //     unfinished: e.detail.value
        // });
    },
    async confirmFinishedService(e) {
        // console.log(e.target.dataset.id)
        if (!e.target.dataset.id) return;
        const res = await requestUtil({ url: '/order/manage/confirmFinishedService?order_id=' + e.target.dataset.id });
        this.getOrder(2);
        console.log(res);

    },
    async employTakeOrder(e){
        if (!e.target.dataset.id) return;
        const res = await requestUtil({ url: '/order/manage/employTakeOrder?order_id=' + e.target.dataset.id });
        this.getOrder(1)
        console.log(res);
    },
    async cancelOrder(e){
        let that =this;
        console.log(e);
        wx.showModal({
            title: '提示',
            content: '确认拒接',
            success: async(confirm)=> {
                if (!confirm.confirm) return;
                const res = await requestUtil({
                     url: '/order/manage/refuseOrder?order_id=' + e.target.dataset.id,
                     data: {
                        order_id:  e.currentTarget.dataset.id,
                        user_id: e.currentTarget.dataset.userid
                     }
                    
                    });
                if (res.code == 500) {
                    console.log(res)
                    wx.showToast({
                        title: res.msg,
                        icon: 'error',
                        duration: 1500
                    });
                }
                else {
                    that.data.QueryParams.page=1;
                    that.setData({
                        QueryParams:that.data.QueryParams
                    })
                    that.getOrder(that.data.showItemIndex)
                    wx.showToast({
                        title: '拒接成功',
                        icon: 'success'
                    });
                }

            }
        });
        
    },
    onScrolltolower() {
        let QueryParams = this.data.QueryParams;
        if (QueryParams.page >= this.data.totalPage) {
            console.log("已经到底部了");
            return;
        }
        QueryParams.page++;
        this.setData({
            QueryParams
        })
        this.getOrder(this.data.showItemIndex, "add");
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

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