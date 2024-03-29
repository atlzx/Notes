import {createRouter,createWebHashHistory} from 'vue-router'  /* 导入vue-router的 createRouter 方法和 createWebHashHistory  */
/*  导入各个vue对象到router.js内,以便进行路由对象的创建  */
import Home from '../components/Home.vue'
import Add from '../components/Add.vue'
import Update from '../components/Update.vue'
import List from '../components/List.vue'


/* 定义一个router对象，调用createRouter方法传入参数 */
const router=createRouter(
    {
        /* 
            createWebHashHistory() 是 Vue.js 基于 hash 模式创建路由的工厂函数。在使用这种模式下，路由信息保存在 URL 的 hash 中
            使用 createWebHashHistory() 方法，可以创建一个路由历史记录对象，用于管理应用程序的路由。在 Vue.js 应用中，通常使用该方法来创建路由的历史记录对象
            就是路由中缓存历史记录的对象，vue-router提供

            省流:创建记载路由历史的对象
        */
        history:createWebHashHistory(),  
        routes:[
            {
                path:'/',  /* path表示项目请求路径 */
                /* 
                    component指定组件在默认的路由视图位置展示，如：component:Home
                    components指定组件在name为某个值的路由视图位置展示
                */
                components:{
                    default:Home,  /* default表示默认以该对象打开，Home对象在之前已经导入，表示默认打开该对象 */
                    homeView:Home  /* homeView对应的是App.vue文件中的router-view标签中的name属性，表示这个name属性为homeView的玩意指向Home文件对象 */
                }
            },
            {
                path:'/add',
                components:{
                    addView:Add
                }
            },
            {
                path:'/update',
                components:{
                    updateView:Update
                }
            },
            {
                path:'/home',
                components:{
                    homeView:Home
                }
            },
            {
                path:'/list',
                components:{
                    listView:List
                }
            },
        ]
    }
);

export default router