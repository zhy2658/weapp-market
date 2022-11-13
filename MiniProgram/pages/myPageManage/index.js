// pages/myPageManage/index.js
// 导入request请求工具方法
import { getBaseUrl, requestUtil } from "../../utils/requestUtil.js";
import { formatDate, formatDateYMD, getTodayBeginAndEnd } from "../../utils/tools.js";
import regeneratorRuntime from '../../lib/runtime/runtime';

const datetime = {
    begin: "2022-11-4 12:00:00",
    end: "2022-12-1 12:00:00",
}


Page({

    /**
     * 页面的初始数据
     */
    data: {
        gridCol:3,
        iconList: [{
            icon: 'copy',
            color: 'red',
            badge: 120,
            name: '开始接单',
            url: "/pages/randomOrder/index"
        }, {
            icon: 'list',
            color: 'orange',
            badge: 1,
            name: '订单管理',
            url:"/pages/orderManage/index"
        }, {
            icon: 'profile',
            color: 'yellow',
            badge: 0,
            name: '我的主页',
            url:"/pages/userDetail/index"
        }, {
            icon: 'similar',
            color: 'olive',
            badge: 0,
            name: '服务选项',
            url: "/pages/serviceOption/index"
        },
        //  {
        //     icon: 'rankfill',
        //     color: 'cyan',
        //     badge: 0,
        //     name: '统计'
        // },
        //  {
        //     icon: 'edit',
        //     color: 'blue',
        //     badge: 0,
        //     name: '我的发布',
        //     url: '/pages/mypublish/index'
        // }
    ],
        userStatus: ["下线", "在线接单"],
        currentStatus: 0,
        basicInfo: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        let userInfo = wx.getStorageSync('userInfo');
        this.setData({
            currentStatus: userInfo.ustatus,
            userInfo
        });
        this.getOrderCount();
    },
    async getOrderCount() {
        let beginAndEnd = getTodayBeginAndEnd();
        datetime.begin = beginAndEnd[0];
        datetime.end = beginAndEnd[1];
        console.log(datetime)
        const result = await requestUtil({
            url: "/order/manage/getOrderCount",
            data: datetime,
            method: "POST",
        });
        if (result.code == 0) {
            result.todayRevenue = result.todayRevenue.toFixed(0)
            this.setData({
                basicInfo: result
            })
        }
        console.log(result)
    },
    async changeStatus(e) {
        let currentStatus = this.data.currentStatus;
        currentStatus = currentStatus == 1 ? 0 : 1;
        this.setData({
            currentStatus
        })
        let userInfo = wx.getStorageSync('userInfo');
        console.log(userInfo)
        let formObj = {
            id: userInfo.id,
            openid: userInfo.openid,
            ustatus: currentStatus
        };
        // console.log(this.data.currentStatus)
        const result = await requestUtil({
            url: "users/update_user",
            method: "POST",
            data: {
                userInfo: formObj
            }
        });
        if (result.code == 0) {
            console.log(result)
            userInfo.ustatus = currentStatus;
            wx.setStorageSync('userInfo', userInfo);
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