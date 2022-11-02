
const baseUrl= "http://localhost:8080/"
// const baseUrl= "https://www.qingnianshiwen.cn/"

export function getServerUrl(){
    return baseUrl;
}

export function filePathHandler(path){
    if(path.indexOf("https://") >-1 || path.indexOf("https://")  >-1)  return path;
   else return baseUrl+path;
}