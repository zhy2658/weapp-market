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
