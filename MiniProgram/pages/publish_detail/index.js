// pages/publish_detail/index.js
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import { getDateDiff } from "../../utils/tools.js";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        publishObj:{
            userInfo:{},
            replyList:[],
            likeList:[],
            imgList:[],
            currentReplayPid: -1,
            showCommentDialog: false
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getOne(options.pid);
    },
    async getOne(pid) {
        const result = await requestUtil({
            url: "publish/selectone",
            data: {
                pid: pid,
            }
        });
        let publishObj = result.publish;
        let userInfo = wx.getStorageSync('userInfo');
        for (let like of publishObj.likeList) {
            if (like.openId == userInfo.openid) {
                publishObj.flagLike = true;
            }
            else{
                publishObj.flagLike = false;
            }
        }
        publishObj.LikeCount = publishObj.likeList.length;
        publishObj.dateDiff=getDateDiff(publishObj.pubtime)
        console.log( publishObj)
        // let DataSource = this.data.DataSource;
        // for (let key in DataSource) {
        //     if (DataSource[key].pid == changedPublish.pid) {
        //         DataSource[key] = changedPublish;
        //         break;
        //     }
        // }
        this.setData({
            publishObj
        })
    },
    openCommentDialog(e) {
        console.log(e.currentTarget.dataset.name)
        this.setData({
            currentReplayPid: e.currentTarget.dataset.name,
            showCommentDialog: true
        });
    },
    closeCommentDialog() {
        this.setData({
            showCommentDialog: false
        });
    },
    // 点击点赞的人
    TouchZanUser: function (e) {
        wx.showModal({
            title: e.currentTarget.dataset.name,
            showCancel: false
        })
    },
    async changeLike(e) {
        console.log(e.currentTarget.dataset.name)
        const result = await requestUtil({
            url: "publish/changlike",
            data: {
                pid: e.currentTarget.dataset.pid
            }
        });
        this.getOne(e.currentTarget.dataset.pid);
    },
    onShareAppMessage(e){
        console.log("share")
        let pid=this.data.publishObj.pid;
        console.log(this.data.publishObj)
        return {
            title: this.data.publishObj.title,
            desc: this.data.publishObj.content,
            path: '/pages/publish_detail/index?pid='+pid
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