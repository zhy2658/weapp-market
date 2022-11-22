// pages/messageList/index.js

import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        baseUrl: "",
        msgSessionList: [],
        userInfo:{}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onShow() {
        this.getMsgList();
        wx.hideTabBarRedDot({
            index: 2
        })
    },
    onLoad(options) {
        const baseUrl = getBaseUrl();

        clearInterval(this.data.getLocalMsgListSign)
        let getLocalMsgListSign=setInterval(()=>{
            this.getLocalMsgList()
        },2000)
        
        this.setData({
            baseUrl: baseUrl,
            userInfo:wx.getStorageSync('userInfo'),
            getLocalMsgListSign
        })

    },
    // 在本地获取
    getLocalMsgList(){
        let result=wx.getStorageSync('msgSessionList');
         // 时间排序
         if(!result)return;
         for(let i=0;i<result.length-1;i++){
            for(let j=i;j<result.length-1;j++){
                if(result[j]["message"].time < result[j+1]["message"].time){
                    let tempObj=result[j];
                    result[j]=result[j+1];
                    result[j+1]=tempObj;
                }
            }
        }
        // 裁剪时间
        // for(let i=0;i<result.length;i++){
        //     if(result[i]["message"].time){
        //         result[i]["message"].time=result[i]["message"].time.split(" ")[0];
        //     }
        // }
        this.setData({
            msgSessionList: result
        });  
    },
    // 服务器获取消息
    async getMsgList() {
        const result = await requestUtil({
            url: "/chat/getAllMsgSession",

        });
        if(!result)return;
        wx.setStorageSync('msgSessionList', result);
        // let result=wx.getStorageSync('msgSessionList');
        console.log(result)
        // 时间排序
        for(let i=0;i<result.length-1;i++){
            for(let j=i;j<result.length-1;j++){
                if(result[j]["message"].time < result[j+1]["message"].time){
                    let tempObj=result[j];
                    result[j]=result[j+1];
                    result[j+1]=tempObj;
                }
            }
        }
        // 裁剪时间
        // for(let i=0;i<result.length;i++){
        //     if(result[i]["message"].time){
        //         result[i]["message"].time=result[i]["message"].time.split(" ")[0];
        //     }
        // }
        this.setData({
            msgSessionList: result
        });  
        // console.log(this.data.msgSessionList)
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
    async openchatroom(e){
        // console.log(e.currentTarget.dataset.openid)
        let targetOpenId=e.currentTarget.dataset.openid;
        let messageid=e.currentTarget.dataset.messageid;
        console.log(e)
        
        const result = await requestUtil({
            url: "/readMsg?id="+messageid,
        });
        wx.navigateTo({
            url: '/pages/chatroom/index?openid='+targetOpenId
          });

    },
    async removeItem(e){
        console.log(e.target.dataset.index)
        let msgSessionList= [];
        for(let key in this.data.msgSessionList){
            console.log(key)
            if(key != e.target.dataset.index){
                msgSessionList.push(this.data.msgSessionList[key])
            }
            else{
                const result = await requestUtil({
                    url: '/removeItem?ms_id='+this.data.msgSessionList[key].ms_id
        
                });
            }
        }
      
        console.log(msgSessionList)
        wx.setStorageSync('msgSessionList', msgSessionList);
        this.setData({
            msgSessionList
        });
        // const result = requestUtil({
        //     url: "/chat/getAllMsgSession",

        // },false);
        // this.getMsgList();
        return false;
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



    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {
        clearInterval(this.data.getLocalMsgListSign)
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {
        clearInterval(this.data.getLocalMsgListSign)
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