import { createApp } from 'vue'  /*  从vue文件中获取createApp方法  */
import './style.css'  /*  文本之所以会居中是因为main.js文件中默认导入了该css文件  */
import App from './App.vue'  /* 将App.vue文件作为对象引入 */
import './style/App.css'  /*  可以直接通过main.js导入，使用该方法导入会使css作用与全体组件  */


/*  调用createApp方法将vue文件对象转换为vue对象，然后调用mount方法使其与id为app的HTML元素关联，即将vue中的内容写到该元素内  */
createApp(App).mount('#app');
