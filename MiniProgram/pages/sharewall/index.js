// pages/sharewall/index.js
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";

var app = getApp()
var that

const queryForm={
    query:'',
    pageNum:1,
    pageSize: 6
  }

Page({
    /**
     * 页面的初始数据
     */
    data: {
        DataSource: [],

        showCommentDialog: false,
        currentReplayPid: -1,
        photoWidth: wx.getSystemInfoSync().windowWidth / 5,

        popTop: 0, //弹出点赞评论框的位置
        popWidth: 0, //弹出框宽度
        isShow: true, //判断是否显示弹出框,

        isBottom:false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        that = this;
        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })

        this.getRandom();
    },
    async loginForm(data) {
        if (data.detail.value.msg.trim() == "" && this.data.currentReplayPid) return;
        const result2 = await requestUtil({
            url: "publish/add_reply",
            data: {
                pid: this.data.currentReplayPid,
                msg: data.detail.value.msg
            }
        });
        this.updateOne(this.data.currentReplayPid);

    },
    async updateOne(pid) {
        const result3 = await requestUtil({
            url: "publish/selectone",
            data: {
                pid: pid,
            }
        });
        let changedPublish = result3.publish;
        let userInfo = wx.getStorageSync('userInfo');
        for (let like of changedPublish.likeList) {
            if (like.openId == userInfo.openid) {
                changedPublish.flagLike = true;
            }
        }
        let DataSource = this.data.DataSource;
        for (let key in DataSource) {
            if (DataSource[key].pid == changedPublish.pid) {
                DataSource[key] = changedPublish;
                break;
            }
        }
        this.setData({
            DataSource
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
        this.updateOne(e.currentTarget.dataset.pid);
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

    async getRandom(operation) {
        // console.log("getRandom")
        const result2 = await requestUtil({
            url: "publish/get_random",
            data:queryForm
        },);

        let userInfo = wx.getStorageSync('userInfo');
        let publishs = result2.publishs;
        if(result2.publishs.length > 0 )queryForm.pageNum++;
        else {
            this.setData({isBottom:true});
            return;
        }
        for (let obj of publishs) {
            // ========
            // obj.likeList= obj.likeList.concat(obj.likeList);
            // obj.likeList= obj.likeList.concat(obj.likeList)
            // obj.likeList= obj.likeList.concat(obj.likeList)
            // obj.likeList= obj.likeList.concat(obj.likeList);
            // obj.content=obj.content+obj.content+obj.content+obj.content+obj.content+obj.content+obj.content+obj.content+obj.content;

            // ========
            // 点赞过多删掉
            if (obj.likeList.length > 5) {
                obj.LikeCount = obj.likeList.length;
                obj.LikeUnEnd = true;
                obj.likeList.length = 5;
            }
            // 回复过多删掉
            if (obj.replyList.length > 4) {
                obj.replyCount = obj.replyList.length;
                obj.replyUnEnd = true;
                obj.replyList.length = 4;
            }
            //多余内容删掉
            if (obj.content.length > 70) {
                obj.content = obj.content.substr(0, 70);
                obj.content = obj.content + "..."
            }

            obj.flagLike = false;
            for (let like of obj.likeList) {
                if (like.openId == userInfo.openid) {
                    obj.flagLike = true;
                }
            }
        }
        let DataSource = [];
        if(operation == "add"){
            DataSource = this.data.DataSource;
            DataSource=DataSource.concat(result2.publishs);
        }
        else{
            DataSource=result2.publishs;
        }
        this.setData({
            DataSource
        })
    },
    // 点击图片进行大图查看
    LookPhoto: function (e) {
        let imgArr = [];
        for (let obj of e.currentTarget.dataset.list) {
            imgArr.push(this.data.BaseUrl + obj.src);
        }

        wx.previewImage({
            current: e.currentTarget.dataset.photurl,
            urls: imgArr
        })
    },

    // 点击点赞的人
    TouchZanUser: function (e) {
        wx.showModal({
            title: e.currentTarget.dataset.name,
            showCancel: false
        })
    },

    // 删除朋友圈
    delete: function () {
        wx.showToast({
            title: '删除成功',
        })
    },

    // 点击了点赞评论
    TouchDiscuss: function (e) {
        // this.data.isShow = !this.data.isShow
        // 动画
        var animation = wx.createAnimation({
            duration: 300,
            timingFunction: 'linear',
            delay: 0,
        })

        if (that.data.isShow == false) {
            that.setData({
                popTop: e.target.offsetTop - (e.detail.y - e.target.offsetTop) / 2,
                popWidth: 0,
                isShow: true
            })

            // 0.3秒后滑动
            setTimeout(function () {
                animation.width(0).opacity(1).step()
                that.setData({
                    animation: animation.export(),
                })
            }, 100)
        } else {
            // 0.3秒后滑动
            setTimeout(function () {
                animation.width(120).opacity(1).step()
                that.setData({
                    animation: animation.export(),
                })
            }, 100)

            that.setData({
                popTop: e.target.offsetTop - (e.detail.y - e.target.offsetTop) / 2,
                popWidth: 0,
                isShow: false
            })
        }
    },
    onPullDownRefresh: function () {
        this.onRefresh();
    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading()
        //loading 提示框
        wx.showLoading({
            title: 'Loading...',
        })
        this.getRandom();
        console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000)
    },
    onScrolltolowe(){
       this.getRandom("add");
    }
    

})