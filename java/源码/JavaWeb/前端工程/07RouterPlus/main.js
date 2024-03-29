import { createApp } from 'vue'
import App from './App.vue'
import Router from './routers/router.js'

let app=createApp(App);
app.use(Router);
app.mount('#app');
