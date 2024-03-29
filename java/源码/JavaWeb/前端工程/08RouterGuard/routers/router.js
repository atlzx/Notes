import {createRouter,createWebHashHistory,useRouter} from 'vue-router'
import View1 from '../components/View1.vue'
import View2 from '../components/View2.vue'
import Home from '../components/Home.vue'

const router=createRouter(
    {
        history:createWebHashHistory(),
        routes:[
            {
                path:'/',
                components:{
                    default:Home,
                    Home:Home
                },
            },
            {
                path:'/view1',
                components:{
                    View1:View1
                }
            },
            {
                path:'/view2',
                components:{
                    View2:View2
                }
            },
        ]
    }
);

router.beforeEach(
    /* 
        to表示请求要去的路径
        from表示请求来源
        next方法是放行方法，只有执行该方法才会放行，方法支持传入一串路径来进行重定向
    */
    (to,from,next)=>{
        /* 判断要去的路径是否为登陆界面，如果是直接放行 */
        if(to.path==='/'){
            next();
        }else{
            /* 如果不是就进行判断，查看 sessionStorage 中是否存在用户名 */
            let userName=window.sessionStorage.getItem('userName');
            if(userName!==null){
                next();
            }else{
                next('/');  /* 如果没有该用户名，那么不允许访问，强迫其回到登陆界面 */
            }
        }
    }
);

router.afterEach(
    /* 
        to表示请求要去的路径
        from表示请求来源
        后置方法没有next方法，因为路由此时已经进行了跳转
    */
    (to,from)=>{
        console.log(to,from);
        console.log('全局后置守卫');
    }
);



export default router;