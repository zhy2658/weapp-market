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
        // message:{},
        productObj: {},
        disableEidt: true,
        BaseUrl: "",
        userInfo: {},

        imgList: [],

        // 音乐播放器对象
        audioPlayObj: {
            isShowPlayArea: false,
            isPlayAudio: false,
            audioSrc: ''
        },
        // 录音对象
        recorderObj: {
            showRecorder: false
        },
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that = this;
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
        // let imgList = [];
        // for (let imgObj of result.message.productSwiperImageList) {
        //     imgList.push(imgObj.image)
        // }
        this.setData({
            userInfo: result.userInfo,
            // message: result.message,
            productObj: result.message,
            audioPlayObj,
            // imgList
        })
        console.log(this.data.audioPlayObj);
    },
    ChooseImage() {
        wx.chooseImage({
            count: 4, //默认9
            sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album'], //从相册选择
            success: (res) => {
                let productObj = this.data.productObj;
                for (let imgSrc of res.tempFilePaths) {
                    if (productObj.productSwiperImageList.length >= 4) break;
                    productObj.productSwiperImageList.push({
                        image: imgSrc, 
                        productId: productObj.id
                    })
                }
                this.setData({
                    productObj
                })
            }
        });
    },
    DelImg(e) {
        wx.showModal({
            title: '召唤师',
            content: '确定要删除这段回忆吗？',
            cancelText: '再看看',
            confirmText: '再见',
            success: res => {
                if (res.confirm) {
                    this.data.productObj.productSwiperImageList.splice(e.currentTarget.dataset.index, 1);
                    this.setData({
                        productObj: this.data.productObj
                    })
                }
            }
        })
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
    async preSubmitForm(e) {
        let that = this;
        let formObj = e.detail.value;
        console.log(formObj)
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
            if(key=="tags" && formObj[key].split(",").length > 3){
                wx.showToast({
                    title: '最多只有三个标签',
                    icon: 'none'
                });
                return;
            }

        }
        e.detail.value=formObj;

        var productObj = this.data.productObj;
        if (productObj.productSwiperImageList > 4) {
            wx.showToast({
                title: '上传的图片不能超过4张',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        wx.showModal({
            title: '上传个人信息',
            content: '确定上传？',
            success(res) {
                if (res.confirm) {
                    // that.setData({ uploadedImgs: [] })
                    if (productObj.productSwiperImageList.length > 0) {
                        //有图片                     
                        wx.showLoading({
                            title: '正在上传图片',
                            mask: true
                        })
                        let imgTotal = productObj.productSwiperImageList.length;
                        let imgCount = 0;
                        for (let imgObj of productObj.productSwiperImageList) {
                            if (imgObj.image.indexOf("wxfile://") == -1 && imgObj.image.indexOf("http://") == -1 && imgObj.image.indexOf("https://") == -1) {
                                imgCount++;
                                if (imgCount >= imgTotal) that.submitForm(e);
                                continue;
                            }
                            wx.uploadFile({
                                url: getBaseUrl() + 'users/uploadUserImgs',
                                filePath: imgObj.image,
                                name: "file",
                                data: {
                                    "token": wx.getStorageSync('token')
                                },
                                header: {
                                    "token": wx.getStorageSync('token'),
                                    "content-type": "multipart/form-data"
                                },
                                success: function (res) {
                                    console.log("resssssss", res)
                                    if (res.statusCode == 200) {
                                        let sort = imgCount;
                                        let obj = JSON.parse(res.data);
                                        productObj.productSwiperImageList[imgCount].image = obj.data.title;
                                        imgCount++;
                                        console.log("上传图片进度: " + imgCount + " / " + imgTotal);
                                        wx.showToast({
                                            title: "上传图片进度: " + imgCount + " / " + imgTotal,
                                            icon: "none", duration: 1500
                                        })
                                        if (imgCount >= imgTotal) {
                                            console.log("所有图片上传完成！准备提交表单 ")
                                            wx.showToast({
                                                title: "所有图片上传完成！准备提交表单 ",
                                                icon: "none", duration: 1500
                                            })
                                            that.setData({
                                                // uploadedImgs,
                                                productObj
                                            })
                                            that.submitForm(e);
                                            // console.log(that.data.uploadedImgs)
                                        }
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
                    }
                    else {
                        //无图片
                        that.submitForm(e, []);
                    }

                    setTimeout(function () {
                        wx.hideLoading()
                        wx.showToast({
                            title: '上传成功',
                            icon: "success",
                            duration: 2000
                        })
                    }, 4000)
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    },
    async submitForm(e) {
        let formObj = e.detail.value;
        let userInfo = this.data.userInfo;
        userInfo.nickName = formObj.nickName;
        userInfo.sex = formObj.sex == true ? 2 : 1;
        userInfo.age = parseInt(formObj.age);
        userInfo.wxid = formObj.wxid;
        userInfo.tags = formObj.tags;
        // userInfo.small_id=
        let productObj = this.data.productObj;
        productObj.productParaImgs = formObj.productParaImgs;
        productObj.description = formObj.description
        // productObj.productIntroImgs = formObj.productIntroImgs;
        if (productObj.productSwiperImageList.length > 0) {
            productObj.swiper = true;
            productObj.swiperPic = productObj.productSwiperImageList[0].image;
        }

        const result = await requestUtil({
            url: "users/update_user",
            method: "POST",
            data: {
                userInfo: userInfo,
                product: productObj
            }

        });
        if (result.code == 0) {
            this.setData({
                userInfo
            })
            wx.setStorageSync('userInfo', userInfo)
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
                    that.getDetail(that.data.userInfo.openid);
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
    onShow(options) {
        if(this.data.productIntroImgs&&this.data.productIntroImgs.trim() != ''){
            let productObj = this.data.productObj;
            productObj.productIntroImgs=this.data.productIntroImgs.trim();
            console.log(productObj.productIntroImgs)
            this.setData({
                productObj
            })
        }
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