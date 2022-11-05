// app.js
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
        wx.getSystemInfo({
          success: e => {
            this.globalData.StatusBar = e.statusBarHeight;
            let custom = wx.getMenuButtonBoundingClientRect();
            this.globalData.Custom = custom;  
            this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
          }
        })
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