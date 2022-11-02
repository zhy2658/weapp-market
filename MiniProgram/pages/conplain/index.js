const app = getApp();
Page({
    data: {
        CustomBar: app.globalData.CustomBar,
        picker1: ['涉嫌色情', '涉嫌赌博', '利诱（含返利/刷单/抽奖等）', '冒充他人诈骗', '其它'],
        imgList: [],
    },
    pickerChange1(e) {
        console.log(e)
        this.setData({
            index: e.detail.value
        })
    },

    ChooseImage() {
        wx.chooseImage({
            count: 4, //默认9
            sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album'], //从相册选择
            success: (res) => {
                if (this.data.imgList.length != 0) {
                    this.setData({
                        imgList: this.data.imgList.concat(res.tempFilePaths)
                    })
                } else {
                    this.setData({
                        imgList: res.tempFilePaths
                    })
                }
            }
        });
    },
    ViewImage(e) {
        wx.previewImage({
            urls: this.data.imgList,
            current: e.currentTarget.dataset.url
        });
    },
    DelImg(e) {
        wx.showModal({
            title: '确认删除',
            success: res => {
                if (res.confirm) {
                    this.data.imgList.splice(e.currentTarget.dataset.index, 1);
                    this.setData({
                        imgList: this.data.imgList
                    })
                }
            }
        })
    },

    ok(e) {
        wx.vibrateShort({
            type: 'light',
        })
        console.log(e.detail.value)
        let data = e.detail.value;
        if (data.reason==null) {
            wx.showModal({
                title: '未选择《投诉原因》',
                showCancel: false,
            })
            return;
        }

        if (data.describe == "") {
            wx.showModal({
                title: '未填写《投诉描述》',
                showCancel: false,
            })
            return;
        }

        this.setData({
            modalName: 'Modal',
        })

    },

    exit() {
        wx.navigateBack({
            delta: 1
          })
        wx.vibrateShort({
            type: 'light',
        })
        wx.exitMiniProgram({
            success: (res) => {}
        })
    },
})
