import { createApp } from "vue";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from "./App.vue";
import router from "./router";
import store from "./store";
import '././assets/styles/border.css'
import '././assets/styles/reset.css'
import SvgIcon from '@/icons'
import '@/router/permission'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import vue3JsonExcel  from "vue3-json-excel"; 

const app=createApp(App);
SvgIcon(app);
app.use(vue3JsonExcel);
app.component('QuillEditor', QuillEditor);
app.use(store).use(router).use(ElementPlus).mount("#app");
