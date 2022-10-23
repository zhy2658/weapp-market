// pages/userInfo/index.js
// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

let that;
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        productObj: {},
        disableEidt: true,
        BaseUrl: "",
        userInfo: {},

        // 音乐播放器对象
        audioPlayObj: {
            isShowPlayArea: false,
            isPlayAudio: false,
            audioSrc: ''
        },
        // 录音对象
        recorderObj: {
            showRecorder: false
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })

        let userInfo = wx.getStorageSync('userInfo');
        if (!userInfo || !userInfo.openid) return;
        this.setData({
            userInfo
        })
        this.getDetail(userInfo.openid);


    },
    async getDetail(openid) {
        console.log(openid)
        const result = await requestUtil({ url: "/product/detailByopenId", data: { openid } });
        let audioPlayObj = this.data.audioPlayObj;
        audioPlayObj.audioSrc = this.data.BaseUrl + result.message.audio;
        this.setData({
            userInfo: result.userInfo,
            productObj: result.message,
            audioPlayObj
        })
        console.log(this.data.audioPlayObj);
    },
    changeAvatar() {
        let that = this;
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                var tempFilePaths = res.tempFilePaths;
                wx.uploadFile({
                    url: getBaseUrl() + 'users/uploadAvatarUrl',
                    filePath: tempFilePaths[0],
                    name: "file",
                    data: {
                        "token": wx.getStorageSync('token')
                    },
                    header: {
                        "token": wx.getStorageSync('token'),
                        "content-type": "multipart/form-data"
                    },
                    success: function (res) {
                        if (res.statusCode == 200) {
                            wx.showToast({
                                title: "头像修改成功！",
                                icon: "none", duration: 1500
                            })
                            console.log()
                            let data = JSON.parse(res.data);
                            let userInfo = that.data.userInfo;
                            userInfo.avatarUrl = data.data.avatarUrl;
                            that.setData({
                                userInfo
                            })
                            wx.setStorageSync('userInfo', userInfo)
                        }
                    },
                    fail: function (err) {

                        wx.showToast({

                            title: "上传失败",
                            icon: "none",
                            duration: 2000
                        })
                    },
                    complete: function (result) {
                        console.log(result.errMsg)
                    }
                })
            }
        });
    },

    async submitForm(e) {
        // console.log(e.detail.value)
        let userInfo = this.data.userInfo;
        let formObj = e.detail.value;
        formObj.openid=userInfo.openid;
        formObj.id=userInfo.id;
        formObj.sex = formObj.sex == true ? 2 : 1;
        for(let key in formObj){
            if(formObj[key].trim){
                formObj[key]=formObj[key].trim()
            }
            if((key=="nickName" && formObj[key].length > 4) ||  formObj[key].length ==0){
                wx.showToast({
                    title: '昵称长度为1-4位',
                    icon: 'none'
                });
                return;
            }
        }
        

        const result = await requestUtil({
            url: "users/update_user",
            method: "POST",
            data: {
                userInfo: formObj
            }

        });
        if (result.code == 0) {
            this.setData({
                userInfo
            })
            wx.setStorageSync('userInfo', userInfo)
            that.getDetail(userInfo.openid);
            this.setData({ disableEidt: true })
            wx.showModal({
                title: '操作成功！',
                success: function (res) {
                   
                }
            })
        }
    },
    openEdit() {
        this.setData({ disableEidt: false })
    },
    closeEdit() {
        let that = this;
        wx.showModal({
            title: '是否取消编辑',
            success: function (res) {
                if (res.confirm) {
                    that.setData({ disableEidt: true })
                }
            }
        })

    },
    playAudio() {
        let audioPlayObj = this.data.audioPlayObj;
        audioPlayObj.isShowPlayArea = true;
        audioPlayObj.isPlayAudio = true;
        this.setData({
            audioPlayObj
        })
    },
    // 播放器关闭时间
    audioClose() {
        console.log("close")
    },
    // 播放器暂停事件
    audioPause() {
        console.log("pause")
    },
    onFileAddress(data) {
        let audioObj = data.detail;
        console.log("data", audioObj);
        wx.uploadFile({
            url: getBaseUrl() + 'users/uploadRecording?audioTime=' + audioObj.curTime,
            filePath: audioObj.filePath,
            name: "file",
            data: {
                "token": wx.getStorageSync('token')
            },
            header: {
                "token": wx.getStorageSync('token'),
                "content-type": "multipart/form-data"
            },
            success: function (res) {
                if (res.statusCode == 200) {
                    wx.showToast({
                        title: "音频上传成功！",
                        icon: "none", duration: 1500
                    })
                    console.log(res)
                }
            },
            fail: function (err) {

                wx.showToast({

                    title: "上传失败",
                    icon: "none",
                    duration: 2000
                })
            },
            complete: function (result) {
                console.log(result.errMsg)
            }
        })

    },
    showRecorder() {
        this.setData({
            recorderObj: {
                showRecorder: true
            }
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