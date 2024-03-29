import { createApp } from 'vue'
import App from './App.vue'
import router from './routers/router.js'  /* 在main.js中导入暴露出去的router对象 */
let app=createApp(App);
app.use(router);  /* 调用use方法，绑定路由对象 */
app.mount('#app');  /* 挂载app对象 */
