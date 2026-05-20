import {createRouter,createWebHashHistory} from 'vue-router'
import Show1 from '../components/show1.vue'
import Show2 from '../components/show2.vue' 

const router=createRouter(
    {
        history:createWebHashHistory(),
        routes:[
            {
                path:'/',
                component:Show1
            },
            {
                /* 在对应路径的后面，写上 :xxx 的形式来告知vue这是使用路径参数进行传参,冒号后面跟着的是变量的名称 */
                path:'/show1/:id/:language',
                components:{
                    Show1:Show1
                }
            },
            {
                path:'/show2',
                components:{
                    Show2:Show2
                }
            }
        ]
    }
);

export default router;