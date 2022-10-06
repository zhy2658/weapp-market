// pages/orderManage/index.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        modalName: "",
        showItemIndex: 0 ,
        showItem:["正在进行","完成","已退单",]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        let that = this;
        setTimeout(function () {
            that.setData({
                loading: true
            })
        }, 500)
    },
    chooseShow(e){
        this.setData({
            showItemIndex: e.currentTarget.dataset.index
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
        console.log(e.detail.value)
        this.setData({
            unfinished: e.detail.value
        });
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