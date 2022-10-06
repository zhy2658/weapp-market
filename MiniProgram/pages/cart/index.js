// 导入request请求工具方法
import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address:{},
    cart:[],
    baseUrl:'',
    allChecked:false,
    totalPrice:0,
    totalNum:0
  },

  // 点击 获取收货地址
  handleChooseAddress(){
    console.log("获取收货地址")
    wx.chooseAddress({
      success: (result) => {
        console.log(result)
        wx.setStorageSync('address', result)
      },
    })
  },

  // 商品数量的编辑功能
  handleItemNumEdit(e){
  
    const {operation,id}=e.currentTarget.dataset;
    console.log(operation,id);
    let {cart}=this.data;
    const index=cart.findIndex(v=>v.id===id);
    if(cart[index].num===1 && operation===-1){
      wx.showModal({
        title:'系统提示',
        content:'您是否要删除？',
        cancelColor: 'cancelColor',
        success:(res)=>{
          if(res.confirm){
            cart.splice(index,1);
            this.setCart(cart);
          }
        }
      })
    }else{
      cart[index].num+=operation;
      this.setCart(cart);
    }
  },

  // 商品选中
  handleItemChange(e){
    // 获取被修改的商品的id
    const id=e.currentTarget.dataset.id;
    console.log(id);
    // 获取购物车数组
    let {cart}=this.data;
    // 找到被修改的商品对象
    let index=cart.findIndex(v=>v.id===id);
    // 选中状态取反
    cart[index].checked=!cart[index].checked;
    
    this.setCart(cart);
  },

    // 商品全选功能
    handleItemAllCheck(){
      // 获取data中的数据
      let {cart,allChecked}=this.data;
      // 修改值
      allChecked=!allChecked;
      // 循环修改cart数组中的商品修改状态
      cart.forEach(v=>v.checked=allChecked);
      // 修改后的值 填充回data以及缓存中
      this.setCart(cart);
    },


  // 设置购物车状态 同时 重新计算 底部工具栏的数据 全选 总价格 购买的数量
  setCart(cart){
      let allChecked=true;
      // 1 总价格 总数量
      let totalPrice=0;
      let totalNum=0;
      cart.forEach(v=>{
        if(v.checked){
          totalPrice+=v.num*v.price;
          totalNum+=v.num;
        }else{
          allChecked=false;
        }
      })
      // 判断cart数组是否位空
      allChecked=cart.length!=0?allChecked:false;
      // 2 给data赋值
      this.setData({
        cart,
        allChecked,
        totalPrice,
        totalNum
      })
      wx.setStorageSync('cart', cart);
  },

  // 点击结算
  handlePay(){
    // 判断收货地址
    const {address,totalNum}=this.data;
    if(!address){
      wx.showToast({
        title: '您还没有选择收货地址',
        icon: 'none'
      })
      return;
    }
    if(totalNum===0){
      wx.showToast({
        title: '您还没有选购商品',
        icon: 'none'
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/pay/index',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const baseUrl=getBaseUrl();
    this.setData({
      baseUrl
    })
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
    // 获取缓存中的收货地址信息
    const address=wx.getStorageSync('address');
    // 获取缓冲中的购物车数据
    const cart=wx.getStorageSync('cart')||[];
    // 给data赋值
    this.setData({
      address
    })
    this.setCart(cart);
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