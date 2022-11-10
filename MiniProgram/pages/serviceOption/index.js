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
        waitingDeleteId:-1,
        extraPayitemList:[]
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
            url:'/order/manage/deleteExtraServiceItem?id='+this.data.waitingDeleteId  })
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
        console.log(this.data.extraPayitemList)
        
        let submitExtraPayItem = [];
        for(let extraPayItem of this.data.extraPayitemList){
            let exist=false;
            for(let payItem of this.data.serviceItems){
                if(payItem.id == extraPayItem.id){
                    exist=true;
                   break;
                }
            }
            if(!exist && extraPayItem.choosed){
                submitExtraPayItem.push({
                    payitem_id: extraPayItem.id
                });
            }
        }
        console.log(submitExtraPayItem );
        const res=await requestUtil({
            url:'/order/manage/addExtreServiceItem' ,
            data: submitExtraPayItem,
            method:"POST"
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
    chooseServiceOption(e) {
        let index = e.target.dataset.index;
        let extraPayitemList=this.data.extraPayitemList;
        extraPayitemList[index].choosed = extraPayitemList[index].choosed ==true?false :true;
        this.setData({
            extraPayitemList
        })
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
    async showAddModel(){
        this.setData({
            isShowAddModel: true
        })
        const res=await requestUtil({ url:'/order/manage/getExtraServiceItems' });
        if(res.code == 0){
            let extraPayitemList=res.extraPayitemList;
            for(let payItem of this.data.serviceItems){
                for(let extraPayItem of extraPayitemList){
                    if(payItem.id == extraPayItem.id){
                        extraPayItem.choosed=true
                    }
                }   
            }    
            this.setData({
                extraPayitemList: res.extraPayitemList
            })
        }
        // console.log(res);
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