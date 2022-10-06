const io = require("../lib/weapp.socket.io.js");  // 引入 socket.io
import {getBaseUrl,geRootURL} from "./requestUtil.js";
const App = getApp();

let wsStatus = false;
let onSocket = null;
onSocket = io(geRootURL()+":8888", { 
    transports: ['websocket'] ,
    query: {
        'token': wx.getStorageSync('token')
    },
    extraHeaders: {
        'token': wx.getStorageSync('token'), // header增加了token
      },
})// 连接 socket    

export const connect = function (cb) { 

  if (!onSocket) {

    onSocket.on('connect', function (res) { // 监听socket 是否连接成功
      cb(true, onSocket)
      wsStatus = true
    })

    // onSocket.on('login', function (res) {
    //   console.log(res)
    // })
    
    setTimeout(function () { // 超时10秒，返回false
      if (!wsStatus) {
        cb(false, onSocket)
      }
    }, 10000)
    
  } else {
    cb(true, onSocket)
  }
}