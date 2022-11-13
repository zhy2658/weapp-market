import { createRouter, createWebHashHistory } from "vue-router";


const routes = [
  {
    path: "/employee",
    name: "employee",
    component: () =>import( "../views/layout2"),
    // redirect:'/',
    children:[
      {
        path: "/employee_home",
        name: "employee_home",
        component: () =>
          import( "../views/employeePage/home/index"),
      },
      {
        path: "/employee_userInfo",
        name: "employee_userInfo",
        component: () =>
          import( "../views/employeePage/userInfo/index"),
      },
      {
        path: "/employee_order",
        name: "employee_order",
        component: () =>
          import( "../views/employeePage/order"),
      },
      {
        path: "/employee_publish",
        name: "employee_publish",
        component: () =>
          import( "../views/employeePage/publish"),
      },
      {
        path: "/employee_modifyPassword",
        name: "employee_modifyPassword",
        component: () =>
          import( "../views/employeePage/modifyPassword"),
      }
    ]
  },
  {
    path: "/admin",
    name: "admin",
    component: () =>import( "../views/layout"),
    redirect:'/home',
    children:[
      {
        path: "/home",
        name: "home",
        component: () =>
          import( "../views/home/index"),
      },
      {
        path: "/notice",
        name: "notice",
        component: () =>
          import( "../views/notice"),
      },
      {
        path: "/user",
        name: "user",
        component: () =>
          import( "../views/user"),
      },
      {
        path: "/bigType",
        name: "bigType",
        component: () =>
          import( "../views/bigType"),
      },
      {
        path: "/smallType",
        name: "smallType",
        component: () =>
          import( "../views/smallType"),
      },
      {
        path: "/product",
        name: "product",
        component: () =>
          import( "../views/product"),
      },
      {
        path: "/order",
        name: "order",
        component: () =>
          import( "../views/order"),
      },
      {
        path: "/publish",
        name: "publish",
        component: () =>
          import( "../views/publish"),
      },
      {
        path: "/serviceOption",
        name: "serviceOption",
        component: () =>
          import( "../views/serviceOption"),
      },
      {
        path: "/statistics",
        name: "statistics",
        component: () =>
          import( "../views/statistics"),
      },
      {
        path: "/modifyPassword",
        name: "modifyPassword",
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
  }, 
  
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
