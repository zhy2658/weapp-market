// pages/mypublish/index.js
import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";

var app = getApp()
var that

Page({
  /**
   * 页面的初始数据
   */
  data: {
    statusOption: ["未审核","已通过","未通过"],
    DataSource: [{
    avatarUrl: 'https://pic1.zhimg.com/v2-ac3f0eeee5d83d5007da10e07b545092_250x0.jpg?source=172ae18b',
    content: '我大学毕业到一家集团公司的办公室当文员。办公室主任有一特长，即文章写得好，很有思想，公司董事长很器重他，董事长的讲话稿和企业的年终总结等一系列重大文章都是出自他的手笔。',
    resource: ['https://pic1.zhimg.com/v2-ac3f0eeee5d83d5007da10e07b545092_250x0.jpg?source=172ae18b',
      'https://img0.baidu.com/it/u=2441833187,3184827457&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=358',
      'https://pic1.zhimg.com/v2-ac3f0eeee5d83d5007da10e07b545092_250x0.jpg?source=172ae18b',
      'https://pic1.zhimg.com/v2-ac3f0eeee5d83d5007da10e07b545092_250x0.jpg?source=172ae18b',
      'https://pic1.zhimg.com/v2-ac3f0eeee5d83d5007da10e07b545092_250x0.jpg?source=172ae18b'
    ],
    zanSource: ['张三', '李四', '王五', '找钱', '孙俪', '王宝'],
    contnet: [{
        'firstname': '张三',
        'content': '你好漂亮呀！！'
      },
      {
        'firstname': '李四',
        'content': '纳尼！！'
      },
      {
        'firstname': '王五',
        'content': '鬼扯咧'
      },
      {
        'firstname': '王宝',
        'content': '昨晚11点左右，一则郑爽胡彦斌疑似复合的消息刷爆各大论坛，微博在深夜11点热度高达200万直接爆掉，中国意难忘又开始了！！！'
      }
    ]}],
    photoWidth: wx.getSystemInfoSync().windowWidth / 5,

    popTop: 0, //弹出点赞评论框的位置
    popWidth: 0, //弹出框宽度
    isShow: true, //判断是否显示弹出框
    userInfo:{

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    that = this;
    const BaseUrl=getBaseUrl();
    this.setData({BaseUrl})

    this.getMyPublish();
    this.setData({
        userInfo: wx.getStorageSync("userInfo")
    });
    
  },
  async getMyPublish(){
    const result2= await requestUtil({
        url: "publish/get_mypublish",

    });
    this.setData({
        DataSource: result2.publishs
    })
    console.log(result2)
},

  // 点击图片进行大图查看
  LookPhoto: function(e) {
    console.log(e);
    wx.previewImage({
      current: e.currentTarget.dataset.photurl,
      urls: e.currentTarget.dataset.list
    })
  },

  // 点击点赞的人
  TouchZanUser: function(e) {
    wx.showModal({
      title: e.currentTarget.dataset.name,
      showCancel: false
    })
  },

  // 删除朋友圈
  delete: function() {
    wx.showToast({
      title: '删除成功',
    })
  },

  // 点击了点赞评论
  TouchDiscuss: function(e) {
    // this.data.isShow = !this.data.isShow
    // 动画
    var animation = wx.createAnimation({
      duration: 300,
      timingFunction: 'linear',
      delay: 0,
    })

    if (that.data.isShow == false) {
      that.setData({
        popTop: e.target.offsetTop - (e.detail.y - e.target.offsetTop) / 2,
        popWidth: 0,
        isShow: true
      })

      // 0.3秒后滑动
      setTimeout(function() {
        animation.width(0).opacity(1).step()
        that.setData({
          animation: animation.export(),
        })
      }, 100)
    } else {
      // 0.3秒后滑动
      setTimeout(function() {
        animation.width(120).opacity(1).step()
        that.setData({
          animation: animation.export(),
        })
      }, 100)

      that.setData({
        popTop: e.target.offsetTop - (e.detail.y - e.target.offsetTop) / 2,
        popWidth: 0,
        isShow: false
      })
    }
  }
})