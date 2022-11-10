// pages/randomOrder/index.js
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
Page({

    /**
     * 页面的初始数据
     */
    data: {
        orderList: [],
        userInfo:{},
        showItemIndex:1,
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
        let userInfo = wx.getStorageSync('userInfo');
        this.setData({
            userInfo
        })
        this.getOrder(this.data.showItemIndex);
    },
    async getOrder(status, operation) {
        let QueryParams = this.data.QueryParams;
        // QueryParams.status = status;
        this.setData({
            QueryParams
        })
        const res = await requestUtil({
            url: '/order/manage/getRandomOrder',
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
        let userInfo=this.data.userInfo;
        for(let order of orderList){
            if((order.random_sex == -1 || userInfo.sex==order.random_sex) && userInfo.employee_grade >= order.random_grade){
                order.available=true;
            }
            else order.available=false;
        }
        console.log(orderList)
        this.setData({
            orderList: orderList,
            total: res.total,
            totalPage: res.totalPage
        })
        console.log(res)
    },
    async employTakeOrderByRandom(e){
        if (!e.target.dataset.id) return;
        const res = await requestUtil({ url: '/order/manage/employTakeOrderByRandom?order_id=' + e.target.dataset.id });
        if(res.code == 0){
            this.getOrder(1)
        }
        else{
            wx.showToast({
                title: res.msg ,
                icon: 'none',
                duration: 1500
            });
        }
      
        // console.log(res);
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
        let QueryParams = this.data.QueryParams;
        QueryParams.page=1;
        this.setData({
            QueryParams
        })
        this.getOrder(this.data.showItemIndex);
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {
        let QueryParams = this.data.QueryParams;
        console.log(QueryParams.page , this.data.totalPage)
        if (QueryParams.page >= this.data.totalPage) {
            console.log("已经到底部了");
            return;
        }
        QueryParams.page++;
        this.setData({
            QueryParams
        })
        this.getOrder(this.data.showItemIndex, "add");
        // console.log("bottom")
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})