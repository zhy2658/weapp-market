// app.js
import { getBaseUrl, requestUtil } from "./utils/requestUtil.js";
App({
    globalData: {
        index: -1
    },
    onPullDownRefresh: function () {
        this.onRefresh();
    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading();
        setTimeout(function () {
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 2000);
    },
    onLaunch: function() {
        this.audioCtx = wx.createInnerAudioContext();
        this.audioCtx.src = "./files/hint.mp3";
         //轮询获取消息
        setInterval(()=>{
            this.getMsgList()
        },10000);
        wx.getSystemInfo({
          success: e => {
            this.globalData.StatusBar = e.statusBarHeight;
            let custom = wx.getMenuButtonBoundingClientRect();
            this.globalData.Custom = custom;  
            this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
          }
        })
      },

      //轮询获取消息
      async getMsgList() {
          let that=this;
        const result = await requestUtil({
            url: "/chat/getAllMsgSession",

        },true);
        let openId = wx.getStorageSync('openId');
        let msgSessionList=wx.getStorageSync('msgSessionList');
        if(msgSessionList == "" || result.length > msgSessionList.length){
            console.log("------result.length > msgSessionList-")
            that.audioCtx.play();
            wx.setStorageSync('msgSessionList', result);
            wx.showTabBarRedDot({
                // index 是导航栏的索引 就是在第几个导航上显示
                  index: 3,
                })
            return;
        }
        //比较是否与上次不一样
        for(let i=0;i<result.length;i++){
            // console.log("result[i] ",result[i] )
            // console.log(result[i]["message"].time,msgSessionList[i]["message"].time)
            if(!result[i] || !(result[i]["message"])|| !(result[i]["message"].time))continue;
            
            for(let msgSession of msgSessionList){
                
                if( (msgSession.openId == result[i].openId ) &&
                    (msgSession.message.time != result[i]["message"].time  ) &&
                msgSessionList[i]["message"].sendOpenID != openId){
                    that.audioCtx.play();
                    wx.showTabBarRedDot({
                        // index 是导航栏的索引 就是在第几个导航上显示
                          index: 3,
                        })
                    wx.setStorageSync('msgSessionList', result);
                }
            }
            
        }
       
    
    },
})

// 暂时停用聊天
// {
//     "pagePath": "pages/messageList/index",
//     "text": "消息",
//     "iconPath": "icons/_msg.png",
//     "selectedIconPath": "icons/msg.png"
// },

// "pages/publish/index",
// {
//     "name": "说说编辑",
//     "pathName": "pages/publish/index",
//     "query": "",
//     "scene": null
// },

// "pages/mypublish/index",
// {
//     "name": "我的发布",
//     "pathName": "pages/mypublish/index",
//     "query": "",
//     "scene": null
// },

// "pages/userDetail/index",
// {
//     "name": "主页管理",
//     "pathName": "pages/userDetail/index",
//     "query": "",
//     "scene": null
// },

// "pages/editIntro/index",
// {
//     "name": "介绍编辑",
//     "pathName": "pages/editIntro/index",
//     "query": "",
//     "scene": null
// }

// {
//     "pagePath": "pages/category/index",
//     "text": "分类",
//     "iconPath": "icons/_category.png",
//     "selectedIconPath": "icons/category.png"
// },