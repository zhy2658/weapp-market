// pages/chatroom/index.js

import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import { socket, connect } from "../../utils/socket.js";
Page({

    /**
     * 页面的初始数据
     */
    data: {
        motto: 'Hello World',
        total: 0,
        OppositeOpenId: null,
        msgList: [
            // {"username":"星星先生","msg": "hello world"},
            // {"username":"正义小姐","msg": "赞美愚者先生"},
            // {"username":"魔术师小姐","msg": "我需要暗杀一名极光会的成员!!"},
            // {"username":"世界先生","msg": "呵呵，不知道你能付出什么样的代价"},
        ]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })
        console.log(options)
        var that = this;
        that.setData({
            OppositeOpenId: options.openid
        });
        /*
        // 监听socket 是否连接成功
        connect((status, ws) => {
            console.log(status)
            // 连接成功
            if (status) {
                // 向服务端发送消息
                ws.emit('connect', { msg: 'Hello World' }); // 参数一：发送消息的socket名，参数二: 发送的数据
                // 接受服务端传来的消息
                ws.on('connect', (res) => { // 参数一：接收消息的socket名，参数二：返回的信息 function 
                    console.log("connect信息:",res);
                });
                ws.on('server_event', data => {
                    console.log("server_event信息:",data)
                    let msgList = that.data.msgList;
                    msgList.push({ "username": "世界", "msg": data.msg })
                    that.setData({
                        msgList: msgList
                    })

                });
                // ws.on('total', data => {
                //     that.setData({
                //         total: data
                //     })
                //     console.log( data)
                // }); 
                ws.on('disconnect', () => {
                    console.log("已下线")
                })
            } else {
                // ...连接超时
            }
        })
        */

        //获取聊天纪录
        this.getMessage();

        setInterval(()=>{
            this.getMessage();
        },5000);

    },
    async getMessage() {
        // this.setData({ OppositeOpenId:"sssss"});   // 测试
        const result2 = await requestUtil({
            url: "/chat/getMessage?openId=" + this.data.OppositeOpenId,

        },true);
        for (let obj of result2) {
            if (obj.mySelf) {
                obj.avatarUrl = wx.getStorageSync('userInfo').avatarUrl;
            }
        }   
        this.setData({
            msgList: result2
        })
        // console.log(result2)
    },
    async loginForm(data) {
        if (data.detail.value.msg.trim() == "") {
            return;
        }
        else {
            const result = await requestUtil({
                url: "/send?msg=" + data.detail.value.msg + "&openId=" + this.data.OppositeOpenId,

            });
            this.setData({
                msgvalue:""
            });
            this.getMessage();
            // data.detail.value.msg = ""
        }

        // console.log(result)
    },
    InputFocus(e) {
        this.setData({
            InputBottom: e.detail.height
        })
    },
    InputBlur(e) {
        this.setData({
            InputBottom: 0
        })
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