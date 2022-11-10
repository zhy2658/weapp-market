// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

let that;
Page({
    data: {
        // 轮播图数组
        swiperList: [],
        baseUrl: '',
        bigTypeList: [],
        bigTypeList_row1: [
            // {
            //     id: 1,
            //     image: "20221022045741000000756.jpg",
            //     name: "盲盒",
            //     remark: "连麦"
            // }
        ],
        bigTypeList_row2: [],
        hotProductList: [],

        // 通知面板
        isShowNotice: false,

        // 显示遮罩
        isShowLayer: false,

        // 音乐播放器对象
        audioPlayObj: {
            isShowPlayArea: false,
            isPlayAudio: false,
            audioSrc: ''
        },
        // 通知
        notice: {},
        payitemList: [],
        layer: 1,
        minusStatus: "normal",
        serviceIndex: 0,
        coinNum: 0,
        blindbox: {
            sex: 1,
            grade: 1,
            id: 0,
            num: 1
        },
        showBlindbox: false
    },
    onLoad: function () {
        that = this;

        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl })

        this.getSwiperList();
        this.getBigTypeList();
        this.getHotProductList();
        this.getNewNotice();

    },
    async getNewNotice() {
        const result = await requestUtil({ url: "/notice/getNew" });
        this.setData({
            notice: result.notice
        })
        console.log(result)
    },
    // 获取轮播图数据
    async getSwiperList() {
        // requestUtil({ url: "/home/swiperdata" })
        //   .then(result => {
        //     this.setData({
        //       swiperList: result
        //     })
        //   })

        const result = await requestUtil({ url: "/product/findSwiper" });

        const baseUrl = getBaseUrl();
        this.setData({
            swiperList: result.message,
            baseUrl: baseUrl
        })
    },
    blindBox() {
        this.setData({
            showBlindbox: true,
            isShowLayer: true
        })
    },
    closeBlindBox() {
        this.setData({
            showBlindbox: false,
            isShowLayer: false,
            layer: 1
        })
    },
    chooseSexOption(e) {
        this.data.blindbox.sex = e.target.dataset.value;
        this.setData({
            blindbox: this.data.blindbox
        })
    },
    chooseGradeOption(e) {
        this.data.blindbox.grade = e.target.dataset.value;
        this.setData({
            blindbox: this.data.blindbox
        })
    },
    chooseServiceOption(e) {
        this.data.serviceIndex = e.target.dataset.value;
        this.data.blindbox.id = e.target.dataset.id;
        let coinNum = this.data.payitemList[this.data.serviceIndex].price * this.data.blindbox.num;
        this.setData({
            serviceIndex: this.data.serviceIndex,
            blindbox: this.data.blindbox,
            coinNum
        })
    },
    inlayer(e) {
        this.setData({
            layer: e.target.dataset.index
        })

        this.getServiceOption();
    },
    async getServiceOption() {
        const result = await requestUtil({ url: "/playitem/getByAllGrade?grade=" + this.data.blindbox.grade });
        if (result.code == 0) {
            this.setData({
                payitemList: result.payitemList
            })
            let coinNum = this.data.payitemList[this.data.serviceIndex].price * this.data.blindbox.num;
            this.setData({
                coinNum
            })
        }
        // console.log(result)
    },
    // 获取商品大类数据
    async getBigTypeList() {
        const result = await requestUtil({ url: "/bigType/findAll" });
        console.log(result)
        const bigTypeList_row1 = result.message.filter((item, index) => {
            return index < 5;
        })
        const bigTypeList_row2 = result.message.filter((item, index) => {
            return index >= 5;
        })
        this.setData({
            bigTypeList: result,
            bigTypeList_row1,
            // bigTypeList_row2
        })
    },

    // 获取热卖商品
    async getHotProductList() {
        const result = await requestUtil({ url: "/product/findHot" });
        console.log(result.users);
        for (let users of result.users) {
            users.isPlay = false;
            if (users.tags) {
                users.tags = users.tags.split(",");
            }

        }
        this.setData({
            hotProductList: result.users
        })
        console.log(this.data.hotProductList)
    },
    // 大类点击事件处理 存储商品类别到全局数据
    handleTypeJump(event) {
        var index = event.currentTarget.dataset.index;
        console.log("index:" + index)
        const app = getApp();
        app.globalData.index = index;

        wx.switchTab({
            url: '/pages/category/index'
        })
    },
    userAudioPlay(e) {
        let index = e.target.dataset.index;
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        hotProductList[index].isPlay = true;
        this.setData({
            hotProductList,
            audioPlayObj: {
                audioSrc: that.data.baseUrl + hotProductList[index].audio,
                isShowPlayArea: false,
                isPlayAudio: true,
            }
        });
        // this.audioPlay();
    },
    userAudioPause(e) {
        let index = e.target.dataset.index;
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        let audioPlayObj = that.data.audioPlayObj;
        audioPlayObj.isShowPlayArea = false;
        audioPlayObj.isPlayAudio = false;
        this.setData({
            hotProductList,
            audioPlayObj: audioPlayObj
        });
        // this.audioPause();
    },
    // 播放器关闭时间
    audioClose() {
        let hotProductList = this.data.hotProductList;
        for (let hotProduct of hotProductList) hotProduct.isPlay = false;
        this.setData({
            hotProductList
        });
    },
    // 播放器暂停事件
    audioPause() {
        console.log("pause")
    },
    closeNotice() {
        this.setData({ isShowNotice: false, isShowLayer: false })
    },
    openNotice() {
        this.setData({ isShowNotice: true, isShowLayer: true })
    },
    /* 点击减号 */
    bindMinus: function () {
        var num = this.data.blindbox.num;
        // 如果大于1时，才可以减  
        if (num > 1) {
            num--;
        }
        // 只有大于一件的时候，才能normal状态，否则disable状态  
        var minusStatus = num <= 1 ? 'disabled' : 'normal';
        this.data.blindbox.num = num;
        let coinNum = this.data.payitemList[this.data.serviceIndex].price * this.data.blindbox.num;
        // 将数值与状态写回  
        this.setData({
            blindbox: this.data.blindbox,
            minusStatus: minusStatus,
            coinNum
        });
    },
    /* 点击加号 */
    bindPlus: function () {
        var num = this.data.blindbox.num;
        // 不作过多考虑自增1  
        num++;
        // 只有大于一件的时候，才能normal状态，否则disable状态  
        var minusStatus = num < 1 ? 'disabled' : 'normal';
        // 将数值与状态写回  
        this.data.blindbox.num = num;
        let coinNum = this.data.payitemList[this.data.serviceIndex].price * this.data.blindbox.num;
        this.setData({
            blindbox: this.data.blindbox,
            minusStatus: minusStatus,
            coinNum
        });
    },
    async payByCoin() {
        let that = this;
        wx.showModal({
            title: '提示',
            content: '确定下单',
            success(res) {
                if (!res.confirm) return;
                that.payByCoinHandler();

            }
        });
    },
    async payByCoinHandler() {
        let blindbox = this.data.blindbox;
        let payitem = this.data.payitemList[this.data.serviceIndex];
        console.log(blindbox)
        let good = {
            goodsNumber: blindbox.num,  //数量
            goodsPrice: payitem.price,  //单价
            goodsName: payitem.itemName,  //服务名
            itemHours: payitem.itemHours,  //单小时数
            totalHours: payitem.itemHours * blindbox.num, //服务总时长 
            totalPrice: payitem.price * blindbox.num, //总价
            goodsPic: null,
            servant_id: null,

        };
        let coinNum = payitem.price * blindbox.num;
        let goodsList = [good];
        const orderParams = {
            totalPrice: coinNum,
            // address,
            goods: goodsList,
            pm_id: blindbox.id,
            random_grade: blindbox.grade,
            servant_id: null,
            status: 7,  //表示请求随机单委托
            type: 1,    //表示请求随机单委托
            random_sex: blindbox.sex
        }
        console.log(orderParams)
        const res = await requestUtil({ url: "/my/order/payRandom0rder", method: "POST", data: orderParams });
        if(res.code == 0){
            wx.showToast({
                title: '下单成功',
                icon: 'success'
            });
          this.setData({
            showBlindbox: false,
            isShowLayer: false,
            layer: 1
          })  
        }
        else{
            wx.showToast({
                title: '下单失败',
                icon: 'error'
            });
        }
    },
    //页面关闭时，停止播放
    onHide() {
        this.setData({
            isShowPlayArea: false,
            isPlayAudio: false
        });
    },
    onUnload() {
        this.setData({
            isShowPlayArea: false,
            isPlayAudio: false
        });
    },
    onPullDownRefresh: function () {
        // console.log("---")
        this.onRefresh();
    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading()
        //loading 提示框
        wx.showLoading({
            title: 'Loading...',
        })
        console.log("下拉index")
        this.getSwiperList();
        this.getBigTypeList();
        this.getHotProductList();
        // this.getRandom();
        // console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000)
    },




})