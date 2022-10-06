// components/Recorder/Recorder.js

let that;
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        recorderObj: {
            type: Object,
            value: {
                showRecorder: false,   //是否展示播放界面         //路径
            }
        }
        
    },

    /**
     * 组件的初始数据
     */
    data: {
        recorderManager: null,
        curTime: 0,
        duration: 0,

        recorder: {
            status: 'ready', // 'ready' | 'recording'  | 'play'
            text: '点击录制',
        },
        timer: null,
        secondes: 0,
        startTimestamp: 0,
        isupload: false,
        filePath: null,
        showRecorder: false
    },
    observers: {
        'recorderObj.showRecorder': function (val) {
            console.log("showRecordeaaar", val)
            that.setData({
                showRecorder: val
            })
        }
    },
    created() {
        that = this;
    },

    /**
     * 组件的方法列表
     */
    methods: {
        _startRecord() {
            this.data.startTimestamp = Date.now();
            const options = {
                duration: 60000,//指定录音的时长，单位 ms，最大为10分钟（600000），默认为1分钟（60000）
                sampleRate: 16000,//采样率
                numberOfChannels: 1,//录音通道数
                encodeBitRate: 96000,//编码码率
                format: 'mp3',//音频格式，有效值 aac/mp3
                frameSize: 50,//指定帧大小，单位 KB
            }
            // console.log(this.data.recorderManager)

            this.data.recorderManager = wx.getRecorderManager();
            //点击录制
            this.data.recorderManager.start(options);

            //开始录音计时
            this.countDown();

            this.data.recorderManager.onStart(() => {
                console.log('点击录制...')
            })

            //错误回调
            this.data.recorderManager.onError((res) => {
                console.log('录音失败', res);
            })
        },
        _endRecord() {
            clearInterval(this.data.timer);
            this.data.recorderManager.stop();
            this.data.recorderManager.onStop((res) => {
                console.log('停止录音', res)
                let filePath = res.tempFilePath;
                let duration = res.duration;
                if ((Date.now() - this.data.startTimestamp) / 1000 < 2) {
                    wx.showToast({
                        title: '录音时间太短!',
                        icon: 'none',
                        duration: 1500
                    });
                    return;
                }
                console.log("地址:   ", filePath, duration)
                this.setData({ filePath: filePath, duration: duration })

                // this._uploadRecord(filePath, duration)
            })
        },
        getRecordLimit() {
            let that = this;
            wx.getSetting({
                success(res) {
                    if (!res.authSetting['scope.record']) { // 未授权
                        wx.authorize({
                            scope: 'scope.record',
                            success() { // 一次才成功授权
                                that._startRecord()
                            },
                            fail(err) {
                                console.log(err)
                                wx.showModal({
                                    title: '温馨提示',
                                    content: '您未授权录音，该功能将无法使用',
                                    showCancel: true,
                                    confirmText: "授权",
                                    success: function (res) {
                                        if (res.confirm) {
                                            wx.openSetting({
                                                success: (res) => {
                                                    if (!res.authSetting['scope.record']) {
                                                        //未设置录音授权
                                                        wx.showModal({
                                                            title: '提示',
                                                            content: '您未授权录音，功能将无法使用',
                                                            showCancel: false,
                                                            success: function () { }
                                                        })
                                                    } else { // 二次才成功授权
                                                        that._startRecord()
                                                    }
                                                },
                                                fail: function () {
                                                    console.log("授权设置录音失败");
                                                }
                                            })
                                        }
                                    },
                                    fail: function (err) {
                                        console.log("打开录音弹框失败 => ", err);
                                    }
                                })
                            }
                        })
                    } else { // 已授权
                        that._startRecord()
                    }
                }
            })
        },
        countDown() {
            this.data.timer = setInterval(() => {
                this.data.secondes++;
                if (this.data.secondes > 360) {
                    clearInterval(this.data.timer);
                    this.data.recorderManager.stop();
                    this.data.recorderManager.onStop((res) => {
                        console.log('时辰已到，自动跳转')
                        let filePath = res.tempFilePath;
                        let duration = 360000;
                        console.log(filePath, duration)
                        //    this._uploadRecord(filePath, duration)
                    })
                    return;
                }
                this.setData({
                    curTime: this.data.secondes
                });
            }, 1000);
        },
        //     recordBtn: throttle(function () {
        //         let { status } = this.data.recorder;
        //         if(!this.data.isupload){
        //           if (status === 'ready') {
        //             this.getRecordLimit()
        //           } else if (status === 'recording') {
        //             this._endRecord()
        //           }
        //         }
        //    }, 1000),
        recordBtn() {
            let { status } = this.data.recorder;
            if (!this.data.isupload) {
                if (status === 'ready') {
                    this.getRecordLimit()
                } else if (status === 'recording') {
                    this._endRecord()
                }
            }
        },
        recording() {
            this.setData({ recorder: { status: 'recordings', text: '点击录制' } })
            this.getRecordLimit();
        },
        endRecording() {
            this.setData({ recorder: { status: 'play', text: '播放' } })
            this._endRecord();
        },
        playRecord() {
            this.play();
        },
        reRecord() {
            this.setData({
                curTime: 0,
                secondes: 0,
                recorder: {
                    status: 'ready',
                    text: '重新录制'
                }
            })
        },
        //播放录音
        play: function () {
            // 获取innerAudioContext实例
            const innerAudioContext = wx.createInnerAudioContext()
            // 是否自动播放
            innerAudioContext.autoplay = true
            // 设置音频文件的路径
            innerAudioContext.src = this.data.filePath;
            console.log(innerAudioContext.src)
            // 播放音频文件
            innerAudioContext.onPlay(() => {
                console.log('开始播放')
            });
            // 监听音频播放错误事件
            innerAudioContext.onError((res) => {
                console.log(res.errMsg)
                console.log(res.errCode)
            })
        },
        onFileAddress() {
            this.triggerEvent('onFileAddress', {
                filePath: this.data.filePath,
                curTime: this.data.curTime
            })
        },
        onCloseRecorder(){
            this.triggerEvent('onCloseRecorder', null)
            this.setData({
                showRecorder:false
            });
            
        }
    }
})
