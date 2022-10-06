import { createStore } from "vuex";
import router from '@/router'
export default createStore({
  state: {},
  mutations: {},
  actions: {
    // 退出
    logout() {
      window.sessionStorage.clear();
      localStorage.clear()
      router.replace('/login')
    }
  },
  modules: {},
});
