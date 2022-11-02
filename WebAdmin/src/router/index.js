import { createRouter, createWebHashHistory } from "vue-router";


const routes = [

  {
    path: "/",
    name: "首页",
    component: () =>import( "../views/layout"),
    redirect:'/home',
    children:[
      {
        path: "/home",
        name: "首页",
        component: () =>
          import( "../views/home/index"),
      },
      {
        path: "/notice",
        name: "通知管理",
        component: () =>
          import( "../views/notice"),
      },
      {
        path: "/user",
        name: "员工管理",
        component: () =>
          import( "../views/user"),
      },
      {
        path: "/bigType",
        name: "用户大类管理",
        component: () =>
          import( "../views/bigType"),
      },
      {
        path: "/smallType",
        name: "用户小类管理",
        component: () =>
          import( "../views/smallType"),
      },
      {
        path: "/product",
        name: "用户管理",
        component: () =>
          import( "../views/product"),
      },
      {
        path: "/order",
        name: "订单管理",
        component: () =>
          import( "../views/order"),
      },
      {
        path: "/publish",
        name: "说说管理",
        component: () =>
          import( "../views/publish"),
      },
      {
        path: "/serviceOption",
        name: "服务选项",
        component: () =>
          import( "../views/serviceOption"),
      },
      {
        path: "/modifyPassword",
        name: "修改密码",
        component: () =>
          import( "../views/modifyPassword"),
      }
    ]
  },
  {
    path: "/login",
    name: "Login",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import( "../views/login"),
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
