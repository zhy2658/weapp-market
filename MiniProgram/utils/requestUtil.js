// 同时发送异步代码的次数
let ajaxTimes = 0;

const PROTOCOL = "https://";
// 定义公共的url
// const IP = "localhost";
const IP = "www.qingnianshiwen.cn";
const rootURL = PROTOCOL + IP
let baseUrl = rootURL + ":8080/";
// let baseUrl = rootURL + "/";
baseUrl="https://www.qingnianshiwen.cn/"



/**
 * 返回baseUrl
 */
export const geRootURL = () => {
    return rootURL;
}
export const gePROTOCOL = () => {
    return PROTOCOL;
}
export const geIP = () => {
    return IP;
}
export const getBaseUrl = () => {
    return baseUrl;
}

/**
 * wx getUserProfile封装
 * @param {*} param0 
 */
export const getUserProfile = () => {
    return new Promise((resolve, reject) => {
        wx.getUserProfile({
            desc: '获取用户信息',
            success: (res) => {
                console.log(res)
                resolve(res);
            },
            fail: (err) => {
                console.log(err)
                reject(err);
            }
        })
    })
}

/**
 * wx login封装
 * @param {*} param0 
 */
export const getLogin = () => {
    return new Promise((resolve, reject) => {
        // 获取小程序登录成功后的code
        wx.login({
            timeout: 5000,
            success: (res) => {
                console.log(res)
                resolve(res);
            },
            fail: (err) => {
                reject(err);
            }
        })
    })
}

/**
 * promise形式的 小程序的微信支付
 * @param {*} pay 
 */
export const requestPay = (pay) => {
    return new Promise((resolve, reject) => {
        wx.requestPayment({
            ...pay,
            success: (res) => {
                resolve(res);
            },
            fail: (err) => {
                reject(err);
            }
        })
    })
}


/**
 * 后端请求工具类
 * @param {*} params 请求参数
 */
export const requestUtil = (params, disableLoading) => {
    // 判断 url中是否带有 /my/ 请求的是私有的路径 带上header token
    let header = { ...params.header };

    //   if(params.url.includes("/my/") 
    //   || params.url.includes("/send")
    //   || params.url.includes("/chat/getMessage")
    //   || params.url.includes("/chat/getAllMsgSession")
    //   ){
    // 拼接header 带上token
    header["token"] = wx.getStorageSync("token");
    //   }


    ajaxTimes++;


    if (!disableLoading) {
        // 显示加载中 效果
        wx.showLoading({
            title: "加载中",
            mask: true
        });
    }




    var start = new Date().getTime();

    // 模拟网络延迟加载
    // while (true) if (new Date().getTime() - start > 1000 * 1) break;


    return new Promise((resolve, reject) => {
        wx.request({
            ...params,
            header: header,
            url: baseUrl + params.url,
            success: (result) => {
                if (result.data && (result.data.status == "403")) {
                    console.log("success ", result.data.msg)
                    wx.showToast({
                        title: result.data.msg,
                        icon: 'error',
                        duration: 2000
                    })
                    //清除缓存
                    wx.clearStorage({
                        success: (res) => { },
                    })
                } else {
                    resolve(result.data);
                }

            },
            fail: (err) => {
                console.log("err", err)
                reject(err);
            },
            complete: () => {
                ajaxTimes--;
                if (ajaxTimes === 0) {
                    //  关闭正在等待的图标
                    wx.hideLoading();
                }
            }
        });
    })
}