// let MODULE_EXPORT=module.export
export function adminOption(index){
    let option=["普通成员","员工"];
    return option[index]
}
export function sexOption(index){
    let option=["未填写","女","男"];
    return option[index]
}
export function showOption(index){
    let option=["不公开","公开"];
    return option[index]
}

export function showIsTop(index){
    let option=["否","是"];
    return option[index]
}
export function memberGradeOption(){
    // let option=["1级员工","2级员工","3级员工","4级员工","5级员工"];
    let option={
        "1": "1级员工",
        "2": "2级员工",
        "3": "3级员工",
        "4": "4级员工",
        "5": "5级员工"
    }
    return option
}
export function orderStatusOption(){
    // let option=["1级员工","2级员工","3级员工","4级员工","5级员工"];
    let option={
        "0": "未支付",
        "1": "等待接单",
        "2": "正在服务",
        "3": "完成服务",
        "4": "完成订单",
        "5": "请求退单",
        "6": "已退单"
    }
    let optionArr=["未支付","等待接单","正在服务","完成服务","完成订单","请求退单","已退单"];
    return optionArr
}

// export function sexOption(){
//     // let option=["1级员工","2级员工","3级员工","4级员工","5级员工"];
//     let option={
//         "1": "女",
//         "2": "男",
//     }
//     return option
// }
// export memberGradeOption={
//     "1": "1级员工",
//     "2": "2级员工",
//     "3": "3级员工",
//     "4": "4级员工",
//     "5": "5级员工"
// }