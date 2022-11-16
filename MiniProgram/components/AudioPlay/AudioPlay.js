// components/AudioPlay/AudioPlay.js
let that;
Component({
    /**
     * 组件的属性列表
     */
    options: {
        addGlobalClass: true,
    },
    properties: {
        audioPlayObj: {
            type: Object,
            value: {
                isShowPlayArea: false,   //是否展示播放界面
                isPlayAudio: false,     //是否播放音频
                audioSrc: ''            //路径
            }
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        audioCtx: null,
        showPlayArea: false,
        currentTime: 0, //小数转整数 
        duration: 0,  //小数转整数
        time: 0,   //秒数转分钟与秒
        time: 0, //秒数转分钟与秒
        isPlay: false
    },
    created() {
        that = this;
        that.audioCtx = wx.createInnerAudioContext();
        that.audioCtx.onTimeUpdate(this.timeChange)
        that.audioCtx.onPause(()=>{
            console.log("this.data.currentTime ",this.data.currentTime)
            if(this.data.currentTime > 1){
                that.triggerEvent('audioPause', {
                    currentTime: this.data.currentTime
                })
                // this.data.currentTime=0
            }
            
        })
    },
    /**
     * 组件的方法列表
     */
    ready() {
        // 在组件实例刚刚被创建时执行
        // 音频
        // setInterval(function () {
        //     console.log("当前组件src:", that.audioCtx.src)
        // }, 1000)
        // 音频由于网络等原因等待中的回调
        that.audioCtx.onWaiting(() => {
            that.audioCtx.pause() // 等待网络的时候音频暂停
            that.setData({
                waitFlag: true // 标明是onWaiting触发的暂停
            })

        })
        // 音频准备就绪的回调
        that.audioCtx.onCanplay(() => {
            if (that.data.waitFlag) { // 如果是onWaiting触发的暂停，就立即播放
                that.audioCtx.play() // play()方法看上去能重新触发onTimeUpdate()回调
                that.setData({
                    waitFlag: false // 取消相应的flag标志位
                })
            }
        })
    },
    observers: {
        'audioPlayObj.audioSrc': function (val) {
            // console.log("oberseve", val, that.audioCtx)
            if (val && that.audioCtx) {
                that.audioCtx.src = val;//这是音频的地址，一般是从后台获取的。
            }
        },
        "audioPlayObj.isPlayAudio": function (val) {
            if (val) that.audioPlay();
            else that.audioPause();
        },
        "audioPlayObj.isShowPlayArea": function (val) {
            that.setData({
                showPlayArea: val
            })
        }
    },
    pageLifetimes: {
        show: function () {
            // 页面被展示
            //   that.audioCtx.play();
        },
        hide: function () {
            // 页面被隐藏
            // console.log('子组件——————pageLifetimes——————hide')
            that.audioCtx.pause()
        },
        resize: function (size) {
            // 页面尺寸变化
        }
    },
    detached: function () {
        // 在组件实例被从页面节点树移除时执行
        // console.log('子组件————————detached')
        that.audioCtx.pause()
    },
    methods: {
        setAudio(src) {
            that.audioCtx.src = src;
        },
        //播放
        audioPlay() {
            that.audioCtx.play();
            that.setData({
                isPlay: true
            })
            that.triggerEvent('audioPlay', null)
        },
        // 暂停播放
        audioPause() {
            that.audioCtx.pause()
            that.setData({
                isPlay: false
            })
            // that.triggerEvent('audioPause', null)
        },
        // 关闭音频播放界面
        audioClose() {
            that.audioPause();
            that.setData({
                showPlayArea: false
            })
            that.triggerEvent('audioClose', null)
        },
        //时间修改
        timeChange() {
            // console.log(that)
            that.setData({
                currentTime: Math.round(that.audioCtx.currentTime), //小数转整数
                duration: Math.ceil(that.audioCtx.duration), //小数转整数
                time: Math.ceil(that.audioCtx.currentTime),  //秒数转分钟与秒
                time: that.secondToTime(Math.ceil(that.audioCtx.currentTime))//秒数转分钟与秒
            })
        },
        //拖动滑块
        bindchanging(e) {
            var that = this;
            console.log(e)
            //停止播放
            this.audioCtx.pause();
            //设置播放时间
            that.audioCtx.seek(e.detail.value);
            that.setData({
                time: that.audioCtx.currentTime,   //秒数转分钟与秒
                time: that.secondToTime(that.audioCtx.currentTime)//秒数转分钟与秒
            })
        },
        secondToTime(time) {
            const h = parseInt(time / 3600)
            const minute = parseInt(time / 60 % 60)
            const second = Math.ceil(time % 60)

            const hours = h < 10 ? '0' + h : h
            const formatSecond = second > 59 ? 59 : second
            return `${hours > 0 ? `${hours}:` : ''}${minute < 10 ? '0' + minute : minute}:${formatSecond < 10 ? '0' + formatSecond : formatSecond}`
        },
        //拖动滑块结束
        bindchange() {
            this.audioCtx.play();
        },
    }
})
