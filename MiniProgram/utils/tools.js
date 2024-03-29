
var formatDateYMD = function (date) {

    var year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    var newTime = year + '-' +
        month + '-' +
        day;
    return newTime;
}

var formatDate = function (date) {

    var year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    var newTime = year + '-' +
        month + '-' +
        day + ' ' +
        hour + ':' +
        min + ':' +
        sec;
    return newTime;
}


//  时间戳转多少分钟之前
var getDateDiff = function(dateTimeStamp) {
    // 时间字符串转时间戳
    var timestamp = new Date(dateTimeStamp).getTime();
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;
    var year = day * 365;
    var now = new Date().getTime();
    var diffValue = now - timestamp;
    var result;
    if (diffValue < 0) {
        return;
    }
    var yearC = diffValue / year;
    var monthC = diffValue / month;
    var weekC = diffValue / (7 * day);
    var dayC = diffValue / day;
    var hourC = diffValue / hour;
    var minC = diffValue / minute;
    if (yearC >= 1) {
        result = "" + parseInt(yearC) + "年前";
    } else if (monthC >= 1) {
        result = "" + parseInt(monthC) + "月前";
    } else if (weekC >= 1) {
        result = "" + parseInt(weekC) + "周前";
    } else if (dayC >= 1) {
        result = "" + parseInt(dayC) + "天前";
    } else if (hourC >= 1) {
        result = "" + parseInt(hourC) + "小时前";
    } else if (minC >= 1) {
        result = "" + parseInt(minC) + "分钟前";
    } else
        result = "刚刚";
    return result;
}

let getTodayBeginAndEnd = function () {
    let todaystart = formatDateYMD(new Date())
    let todaystart2 = new Date(todaystart)
    let todayend = new Date(todaystart2.getTime() + (1000 * 3600 * 24))
    return [formatDate(todaystart2), formatDate(todayend)]
}
let employeeGrade={
    "1":"普通",
    "2":"金牌",
    "3":"镇店",
    "4":"白金",
}
let getRandomNum =function(range,length){
    let arr=[];
    for(let i=0;i<length;i++){
       arr.push( Math.ceil(Math.random() * range));
    }
    return arr;
}


module.exports = {
    formatDate: formatDate,
    formatDateYMD: formatDateYMD,
    getTodayBeginAndEnd,
    getDateDiff,
    getRandomNum

};
module.exports.msg = "some msg";
module.exports.employeeGrade=employeeGrade;