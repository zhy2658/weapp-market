// 导入request请求工具方法
import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';


Page({
 
    /**
     * 页面的初始数据
     */
    data: {
      imgs: [],
      uploadedImgs:[],
      inputDisable:false
    },
    onLoad(){
        const BaseUrl=getBaseUrl();
        this.setData({BaseUrl})
    },
    //选择图片
    chooseImg: function (e) {
      var that = this;
      var imgs = this.data.imgs;
      if (imgs.length >= 8) {
        this.setData({
          lenMore: 1
        });
        setTimeout(function () {
          that.setData({
            lenMore: 0
          });
        }, 2500);
        return false;
      }
      wx.chooseImage({
        count: 8, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
          // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
          var tempFilePaths = res.tempFilePaths;
          
          var imgs = that.data.imgs;
          // console.log(tempFilePaths + '----');
          for (var i = 0; i < tempFilePaths.length; i++) {
            if (imgs.length >= 8) {
              that.setData({
                imgs: imgs
              });
              return false;
            } else {
            
              imgs.push(tempFilePaths[i]);
            }
          }
          // console.log(imgs);
          that.setData({
            imgs: imgs
          });
        }
      });
    },
    // 删除图片
    deleteImg: function (e) {
      var imgs = this.data.imgs;
      var index = e.currentTarget.dataset.index;
      imgs.splice(index, 1);
      this.setData({
        imgs: imgs
      });
    },
    // 预览图片
    previewImg: function (e) {
      //获取当前图片的下标
      var index = e.currentTarget.dataset.index;
      //所有图片
      var imgs = this.data.imgs;
      wx.previewImage({
        //当前显示图片
        current: imgs[index],
        //所有图片
        urls: imgs
      })
    },
    //检查字数
    checkInput:function(e){
      var that=this;
      if(e.detail.value.length>=100){
        wx.showToast({
          title: '备注不能超过100个字',
          icon:"none",
          duration:2000
        })
      }
    },
    //上传到后台
    sureUpload:function(e){
        let that=this;
        var imgList=this.data.imgs;
        if(imgList.length>8){
            wx.showToast({
            title: '上传的图片不能超过8张',
            icon: 'none',
            duration: 2000
            })
            return false;
        }
        wx.showModal({
            title: '上传作品',
            content: '确定上传？',
            success(res) {
            if (res.confirm) {
                that.setData({uploadedImgs:[]})
                if(that.data.imgs.length > 0 ){
                    //有图片
                    wx.showLoading({
                        title: '正在上传图片',
                        mask:true
                    })
                    let imgTotal=that.data.imgs.length;
                    let imgCount=0;
                    console.log(that.data.imgs)
                    for(let imgsrc of that.data.imgs){
                        wx.uploadFile({           
                            url: getBaseUrl()+'publish/uploadPublishImage',
                            filePath: imgsrc,
                            name: "file",
                            data:{
                                "token":wx.getStorageSync('token')
                           },
                            header: { 
                              "token":wx.getStorageSync('token'),
                              "content-type": "multipart/form-data"
                            },
                            success: function (res) {                
                              if (res.statusCode == 200) {   
                                let sort = imgCount;
                                imgCount++;
                                console.log("上传图片进度: "+imgCount +" / "+imgTotal);
                                wx.showToast({ title: "上传图片进度: "+imgCount +" / "+imgTotal,
                                icon: "none",duration: 1500 })
                                let uploadedImgs =  that.data.uploadedImgs;
                                let obj =JSON.parse(res.data);
                                console.log(obj.data.src)
                                uploadedImgs.push({src: obj.data.src,sort})
                                that.setData({
                                    uploadedImgs
                                })
                                console.log(uploadedImgs.length,imgTotal)
                                if(uploadedImgs.length == imgTotal){
                                    console.log( "所有图片上传完成！准备提交表单 ")
                                    wx.showToast({ title: "所有图片上传完成！准备提交表单 ",
                                    icon: "none",duration: 1500 })
                                    that.uploadFrom(e,that.data.uploadedImgs);
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
                    that.uploadFrom(e,[]);
                }
   
                setTimeout(function () {
                wx.hideLoading()
                wx.showToast({
                    title: '上传成功',
                    icon:"success",
                    duration:2000
                })
                }, 4000)
            } else if (res.cancel) {
                console.log('用户点击取消')
            }
            }
        })
    },
    async uploadFrom(e,uploadedImgs) {
        console.log(e,uploadedImgs);
        var formdata = e.detail.value;
        // console.log(formdata,uploadedImgs);
        let isimg =  uploadedImgs.length > 0 ? 1 : 0; 
        const result= await requestUtil({
            url: "publish/add_publish",
            method:"POST",
            data:{
                "publish":{
                    "title": formdata.title,
                    "content": formdata.content,
                    // "address":"湖北恩施",
                    isimg
                },
                "publishImgs": uploadedImgs
            }
        });
        console.log(result)
        if(result.ok == 200){
            wx.showModal({
                title: '操作成功！',
                content: '已发布等待审核中...',
                success: function (res) {
                    wx.navigateBack({
                        delta: 1
                      })
                }
              })
           
        }
      },
   
   
   
    bindPickerChange(e) {
      console.log('picker发送选择改变，携带值为', e.detail.value)
      this.setData({
        index: e.detail.value
      })
    },
    clearFont() {
      this.setData({
        placeholder: ''
      })
    },
   
    bindRegionChange(e) {
      console.log('picker发送选择改变，携带值为', e.detail.value)
      this.setData({
        region: e.detail.value
      })
    }
  })
