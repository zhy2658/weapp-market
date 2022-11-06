
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


module.exports = {
    formatDate: formatDate,
    formatDateYMD: formatDateYMD,
    getTodayBeginAndEnd

};
module.exports.msg = "some msg";
module.exports.employeeGrade=employeeGrade;