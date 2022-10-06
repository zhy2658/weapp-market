import router from "@/router/index";

const whiteList=['/login']
router.beforeEach((to,from,next)=>{

  let token=window.sessionStorage.getItem("token");
  if(token){
    if(to.path==='/login'){
      next('/')
    }else{
      next();
    }
  }else{
    if(whiteList.includes(to.path)){
      next()
    }else{
      next('/login')
    }
  }
})