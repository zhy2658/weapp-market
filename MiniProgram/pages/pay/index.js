// 导入request请求工具方法
import { getBaseUrl, requestUtil, getLogin, getUserProfile, requestPay } from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 以下是需要提交内容----------------》
        cart: [
            {
                // goodsId: v.id,
                goodsNumber: 1,
                goodsPrice: 200,
                goodsName: "吃饭",
                // serviceStart:"2022-8-7 15:17:12",
                // serviceEnd:"2023-8-7 15:17:12",
                totalHours: 24
                // goodsPic: v.proPic

            }
        ],
        totalPrice: 0,
        totalNum: 1,
        consignee: "",
        telNumber: "",
        currentPayItemId: "",
        servant_id: '',
        // 《----------------以上是需要提交内容


        remark: "",
        // address: {},
        payitemList: [],

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log( " options.id",options)
        let openId = options.openId;
        let employee_grade = options.employee_grade;
        // let serviceStart = new Date();
        // let serviceEnd=new Date();
        // serviceEnd.setHours(serviceEnd.getHours() + (parseInt(options.itemHours) * parseInt(options.num)) )
        // serviceStart=serviceStart.Format("yyyy-MM-dd hh:mm:ss");
        // serviceEnd=serviceEnd.Format("yyyy-MM-dd hh:mm:ss");
        // var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
        let cart = [{
            goodsNumber: options.num,  //数量
            goodsPrice: options.price,  //单价
            goodsName: options.itemName,  //服务名
            itemHours: options.itemHours,  //单小时数
            totalHours: options.itemHours * options.num, //服务总时长 
            totalPrice: options.price * options.num, //服务总时长 
            goodsPic: options.avatarUrl,
            servant_id: openId
        }];
        this.setData({
            cart,
            currentPayItemId: options.id,
            totalNum: options.num,
            totalPrice: options.num * options.price,
            servant_id: openId
        })

        this.getByOpenId(openId,employee_grade);
        const baseUrl = getBaseUrl();
        this.setData({
            baseUrl
        })
    },
    changeTel(e) {
        this.setData({
            telNumber: e.detail.value
        })
    },
    changeRemark(e) {
        this.setData({
            remark: e.detail.value
        })
    },
    changeWXId(e) {
        this.setData({
            consignee: e.detail.value
        })
    },
    async getByOpenId(openId,employee_grade) {
        // console.log(wx.getStorageSync('userInfo'))
        // let grade = wx.getStorageSync('userInfo').employee_grade;
        const result = await requestUtil({ url: "/playitem/getByGrade?grade=" + employee_grade+"&openId="+openId });

        this.setData({
            payitemList: result.payitemList
        })
        console.log(result);



    },
    choosePayItem(e) {
        // console.log()
        this.setData({
            currentPayItemId: e.target.dataset.payitemid
        })
    },

    /**
     * 点击支付
     */
    async handleOrderPay() {
        let that = this;
        wx.showModal({
            title: '提示',
            content: '确定下单',
            success(res) {
                if (!res.confirm) return;
                // 判断缓存中是否有token
                const token = wx.getStorageSync('token');
                // consignee: "",
                // telNumber: "",
                // 手机号表单验证
                // if(isNaN(this.data.telNumber) || this.data.telNumber.trim().length != 11){
                //     wx.showToast({
                //         title: '请填写正确的电话号码',
                //         icon: 'none'
                //     });
                //     return;
                // }
                // 微信号表单验证
                // if(this.data.consignee.trim().length <6 || this.data.consignee.trim().length>25){
                //     wx.showToast({
                //         title: '请填写正确的微信号',
                //         icon: 'none'
                //     });
                //     return;  
                // }
                if (!token) {
                    // const {code}=await login();
                    // wx.getUserProfile({
                    //   desc: '获取用户信息',
                    //   success: (res)=>{
                    //     const {encryptedData,rawData,iv,signature}=res;

                    //     console.log(code,encryptedData,rawData,iv,signature)
                    //   },
                    //   fail:()=>{
                    //     console.log("登录失败")
                    //   }
                    // })

                    Promise.all([getLogin(), getUserProfile()]).then((res) => {
                        console.log(res)
                        let loginParam = {
                            code: res[0].code,
                            nickName: res[1].userInfo.nickName,
                            avatarUrl: res[1].userInfo.avatarUrl
                        }
                        console.log(loginParam);
                        // 把用户信息放到缓存中
                        // wx.setStorageSync('userInfo', res[1].userInfo);
                        that.wxlogin(loginParam);
                    })
                } else {
                    console.log("token:" + token);
                    // 走支付 创建订单
                    that.createOrder(token);
                }
            }

        });


    },

    /**
     * 请求后端获取用户token
     * @param {} loginParam 
     */
    async wxlogin(loginParam) {

        // 发送请求 获取用户的token
        const result = await requestUtil({ url: "/users/wxlogin", data: loginParam, method: "post" });
        console.log("result:", result)

        let token = result.token;
        if (result.code == 0) {
            // 存储token到缓存
            wx.setStorageSync('token', token);
            // 支付继续走 创建订单
            this.createOrder(token);
        }
    },

    /**
     * 创建订单
     */
    async createOrder(token) {
        try {
            // const header={Authorization:token};  // 请求头参数
            const totalPrice = this.data.totalPrice; // 请求体 总价
            //   const address=this.data.address.provinceName+this.data.address.cityName+this.data.address.countyName+this.data.address.detailInfo; // 请求体 收货地址
            const consignee = this.data.consignee; // 请求体 收货人
            const telNumber = this.data.telNumber; // 请求体 联系电话
            const remark = this.data.remark; // 请求体 联系电话
            const pm_id = this.data.currentPayItemId;   //项目id
            const servant_id = this.data.servant_id;
            let goods = this.data.cart;
            //   let goods=[];
            //   购物车
            //   this.data.cart.forEach(v=>goods.push({
            //     // goodsId: v.id,
            //     goodsNumber: v.goodsNumber,
            //     goodsPrice: v.goodsPrice,
            //     goodsName: v.goodsName,
            //     // goodsPic: v.proPic
            //   }))
            //  发送请求 创建订单
            const orderParams = {
                totalPrice,
                // address,
                consignee,
                telNumber,
                remark,
                goods,
                pm_id,
                servant_id
            }
            console.log("orderParams", orderParams)
            // return;
            const res = await requestUtil({ url: "/my/order/payByCoin", method: "POST", data: orderParams });
            if (res.code == 500) {
                setTimeout(() => {
                    // wx.reLanch({
                    //     url: '/pages/userOrder/index'
                    // })
                    wx.navigateTo({
                        url: '/pages/wallet/index',
                    })
                }, 1000)
                wx.showToast({
                    title: "米粒余额不足,请前往充值",
                    icon: 'none'
                })
                return;
            }

            console.log(res.orderNo);
            let orderNo = res.orderNo;
            console.log("orderNo:" + orderNo);

            // const preparePayRes = await requestUtil({ url: "/my/order/payByCoin", method: "POST", data: orderNo });
            // console.log("preparePayRes:" + preparePayRes)
            // let payRes = await requestPay(preparePayRes);


            //------------  现金支付---------------------2022-11-6注释
            // const preparePayRes = await requestUtil({ url: "/my/order/preparePay", method: "POST", data: orderNo });
            // console.log("preparePayRes:" + preparePayRes)
            // let payRes = await requestPay(preparePayRes);

            // if(payRes.errMsg.indexOf("ok") != 0){
            //     const preparePayRes = await requestUtil({ url: "/my/order/setOrderStatus", method: "POST",
            //      data:  {
            //             orderNo:orderNo
            //         } 
            //     });
            // }
            // console.log(payRes)
            //---------------现金支付---------------------2022-11-6注释

            //  《《《======支付接口 暂不写   

            // 删除缓冲中 已经支付的商品
            // let newCart = wx.getStorageSync('cart');
            // newCart = newCart.filter(v => !v.checked);

            // wx.setStorageSync('cart', newCart);



            setTimeout(() => {
                // wx.reLanch({
                //     url: '/pages/userOrder/index'
                // })
                wx.navigateTo({
                    url: '/pages/userOrder/index?status=1',
                })
            }, 500)

            this.getDetail();
            wx.showToast({
                title: '下单成功',
                icon: 'success'
            });
        } catch (error) {
            console.log(error);
            wx.showToast({
                title: '下单失败',
                icon: 'none'
            })

        }

    },
    async getDetail() {
        let userInfo = wx.getStorageSync('userInfo');
        const result = await requestUtil({ url: "/product/detailByopenId", data: { openid: userInfo.openid } });
        // let audioPlayObj = this.data.audioPlayObj;
        // audioPlayObj.audioSrc = this.data.BaseUrl+ result.message.audio;
        userInfo = result.userInfo;
        userInfo.coin = userInfo.coin.toFixed(0);
        this.setData({
            userInfo: result.userInfo,
            // productObj: result.message,
            // audioPlayObj
        })
        wx.setStorageSync('userInfo', userInfo)
    },


    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // 1，获取缓存中的收货地址信息
        const address = wx.getStorageSync('address');
        // 获取缓冲中的购物车数据
        let cart = wx.getStorageSync('cart') || [];
        // 过滤后的购物车数组
        cart = cart.filter(v => v.checked);

        // 1 总价格 总数量
        let totalPrice = 0;
        let totalNum = 0;
        cart.forEach(v => {
            totalPrice += v.num * v.price;
            totalNum += v.num;
        })
        // 2 给data赋值
        this.setData({
            // cart,
            // totalPrice,
            // totalNum,
            //   address
        })
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

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}