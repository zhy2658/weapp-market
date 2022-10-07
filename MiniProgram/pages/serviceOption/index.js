// pages/serviceOption/index.js

import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isShowDeleteModel: false,  //删除框
        isShowAddModel: false,  //增加框
        serviceItems:[],
        waitingDeleteId:-1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getServiceItem();
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },
    async confirmDelete(){
        const res=await requestUtil({
            url:'/order/manage/deleteServiceItems?payitem_id='+this.data.waitingDeleteId  })
        if(res.code === 500){
            console.log(res)
        }
        else{
            this.setData({
                isShowDeleteModel:false,
            });
            this.getServiceItem();
        }
    },
    async addServiceItem(e){
        let form =e.detail.value;
        const res=await requestUtil({
            url:'/order/manage/createServiceItem' ,
            data:form
        }   );
        if(res.code == 500){
            console.log(res)
        } 
        else{
            this.setData({
                isShowAddModel:false,
            });
            this.getServiceItem();
        }
    },
    async getServiceItem(){
        const res=await requestUtil({ url:'/order/manage/getServiceItems' });
        
        if(res.code == 0){
            this.setData({
                serviceItems: res.payItems
            })
        }
        console.log(this.data.serviceItems);

    },

    showDeleteModal(e) {
        this.setData({
            isShowDeleteModel: true,
            waitingDeleteId: e.target.dataset.id
        })
    },
    hideDeleteModal(e) {
        this.setData({
            isShowDeleteModel: false
        })
    },
    showAddModel(){
        this.setData({
            isShowAddModel: true
        })
    },
    hideAddModel(){
        this.setData({
            isShowAddModel: false
        })
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