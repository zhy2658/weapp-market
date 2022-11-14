// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import { getRandomNum } from "../../utils/tools.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        options:{},
        ColorList: app.globalData.ColorList,
        productObj: {},
        userInfo: {},
        baseUrl: '',
        activeIndex: 0,
        sexOption: ["不明", "女", "男"],

        isPlay: false,

        // 音乐播放器对象
        audioPlayObj: {
            isShowPlayArea: false,
            isPlayAudio: false,
            audioSrc: ''
        },
        // 模态框属性------------------》
        showModalStatus: false,//是否显示
        gg_id: 0,//规格ID
        gg_txt: '',//规格文本
        gg_price: 0,//规格价格
        gg_hours: 0,
        payitemList: [
            // {id:1, itemName: '100',itemHours:5, price: '150' }, 
            // { id:2,guige: '200', price: '150' }, 
            // {id:3, guige: '300', price: '150' }
        ],
        num: 1,//初始数量,
        // <-------------------------------
    },

    productInfo: {

    },

    /**
     * tab点击事件
     * @param {} e 
     */
    handleItemTap(e) {
        console.log(e)
        const { index } = e.currentTarget.dataset;
        console.log(index);
        this.setData({
            activeIndex: index
        })
    },

    // 获取产品信息
    async getProductDetail(id) {
        let myUserInfo=wx.getStorageSync('userInfo')
        const result = await requestUtil({ url: "/product/detail", data: { 
            id:id,
            myOpenId:myUserInfo.openid 
        
        } });
        this.productInfo = result.message;
        const baseUrl = getBaseUrl();
        let audioPlayObj = this.data.audioPlayObj;
        audioPlayObj.audioSrc = baseUrl + result.message.audio;
        if (result.userInfo.tags) { 
            result.userInfo.tags = result.userInfo.tags.split(",");
            result.userInfo.randomArr=getRandomNum(10,10)
         }
        this.setData({
            userInfo: result.userInfo,
            productObj: result.message,
            payitemList: result.payitemList,
            baseUrl,
            audioPlayObj,
            isdescribe:result.isdescribe,
        })

    },
    async addAttention(e){
        const result2 = await requestUtil({url: "attention/add?opposite_id="+this.data.userInfo.openid },);
        this.getProductDetail(this.data.options.id)
    },
    async removeAttention(e){
        const result2 = await requestUtil({url: "attention/remove?opposite_id="+this.data.userInfo.openid },);
        this.getProductDetail(this.data.options.id)

    },
    choosePayItem() {


        this.showModal()
        // console.log("aa")
    },
    // 加入购物车
    setCartAdd() {
        // 获取缓存中的购物车 数组格式
        let cart = wx.getStorageSync('cart') || [];
        // 判断商品对象中是否存在于购物车数组中
        let index = cart.findIndex(v => v.id === this.productInfo.id);
        if (index === -1) {  // 不存在
            this.productInfo.num = 1;
            this.productInfo.checked = true;
            cart.push(this.productInfo);
        } else {  // 已经存在
            cart[index].num++;
        }
        wx.setStorageSync('cart', cart); // 把购物车添加到缓存中
    },
    openHome(){
        wx.switchTab({
            url: '/pages/index/index'
        });
    },

    // 点击 加入购物车
    handleCartAdd() {
        this.setCartAdd();
        // 弹窗提示
        wx.showToast({
            title: '加入成功',
            icon: 'success',
            mask: true
        })
    },
    playAudio() {
        let audioPlayObj = this.data.audioPlayObj;
        audioPlayObj.isShowPlayArea = false;
        audioPlayObj.isPlayAudio = true;
        this.setData({
            audioPlayObj,
            isPlay: true
        })
    },
    pauseAudio() {
        let audioPlayObj = this.data.audioPlayObj;
        audioPlayObj.isShowPlayArea = false;
        audioPlayObj.isPlayAudio = false;
        this.setData({
            audioPlayObj,
            isPlay: false
        })
    },
    // 播放器关闭时间
    audioClose() {
        this.setData({ isPlay: false })
        console.log("close")
    },
    // 播放器暂停事件
    audioPause() {
        this.setData({ isPlay: false })
        console.log("pause")
    },
    audioPlay() {
        this.setData({ isPlay: true })
    },
    // 点击 立即购买
    handleBuy() {
        this.setCartAdd();
        wx.switchTab({
            url: '/pages/cart/index',
        })
    },
    // 点击图片进行大图查看
    LookPhoto: function (e) {
        let imgArr = [];
        for (let obj of e.currentTarget.dataset.list) {
            imgArr.push(this.data.baseUrl + 'image/productSwiperImgs/' + obj.image);
        }
        wx.previewImage({
            current: e.currentTarget.dataset.photurl,
            urls: imgArr
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        const BaseUrl = getBaseUrl();
        this.setData({ BaseUrl,options })
        // options.id=12
        this.getProductDetail(options.id)
    },
    confirmOrder() {
        let userInfo = wx.getStorageSync('userInfo');
        let id = this.data.gg_id;
        let itemName = this.data.gg_txt;
        let price = this.data.gg_price;
        let itemHours = this.data.gg_hours;
        let openId = this.data.userInfo.openid;
        let avatarUrl=this.data.userInfo.avatarUrl;
        let num=this.data.num;
        if(!id){
            wx.showToast({  
                title: "请选择服务！",  
                icon: 'error',  
                duration: 2000  
            })  
            return;
        }
        if(!userInfo.openid){
            // wx.showToast({  
            //     title: "请登录！",  
            //     icon: 'error',  
            //     duration: 2000  
            // })  
            wx.switchTab({
                url: '/pages/my/index'
              });
            return;
        }
        // if(openId == userInfo.openid){
        //     wx.showToast({  
        //         title: "是自己额！",  
        //         icon: 'error',  
        //         duration: 2000  
        //     })  
        //     return;
        // }
        wx.navigateTo({   //保留当前页面，跳转到应用内的某个页面（最多打开5个页面，之后按钮就没有响应的）后续可以使用wx.navigateBack 可以返回;
            url: `/pages/pay/index?id=${id}&itemName=${itemName}&price=${price}&itemHours=${itemHours}&openId=${openId}&num=${num}&avatarUrl=${avatarUrl}`
        })
    },


    //模态框事件------------------》
    filter: function (e) {
        //console.log(e);
        var self = this, id = e.currentTarget.dataset.id, txt = e.currentTarget.dataset.txt,
            price = e.currentTarget.dataset.price,
            gg_hours = e.currentTarget.dataset.hours;
        self.setData({
            gg_id: id,
            gg_txt: txt,
            gg_price: price,
            gg_hours,

        });
    },

    /* 点击减号 */
    bindMinus: function () {
        var num = this.data.num;
        // 如果大于1时，才可以减  
        if (num > 1) {
            num--;
        }
        // 只有大于一件的时候，才能normal状态，否则disable状态  
        var minusStatus = num <= 1 ? 'disabled' : 'normal';
        // 将数值与状态写回  
        this.setData({
            num: num,
            minusStatus: minusStatus
        });
    },
    /* 点击加号 */
    bindPlus: function () {
        var num = this.data.num;
        // 不作过多考虑自增1  
        num++;
        // 只有大于一件的时候，才能normal状态，否则disable状态  
        var minusStatus = num < 1 ? 'disabled' : 'normal';
        // 将数值与状态写回  
        this.setData({
            num: num,
            minusStatus: minusStatus
        });
    },

    //显示对话框
    showModal: function () {
        // 显示遮罩层
        var animation = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        })
        this.animation = animation
        animation.translateY(300).step()
        this.setData({
            animationData: animation.export(),
            showModalStatus: true
        })
        setTimeout(function () {
            animation.translateY(0).step()
            this.setData({
                animationData: animation.export()
            })
        }.bind(this), 200)
    },
    //隐藏对话框
    hideModal: function () {
        // 隐藏遮罩层
        var animation = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        })
        this.animation = animation
        animation.translateY(300).step()
        this.setData({
            animationData: animation.export(),
        })
        setTimeout(function () {
            animation.translateY(0).step()
            this.setData({
                animationData: animation.export(),
                showModalStatus: false
            })
        }.bind(this), 200)
    },
    //      《------------------模态框事件

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})