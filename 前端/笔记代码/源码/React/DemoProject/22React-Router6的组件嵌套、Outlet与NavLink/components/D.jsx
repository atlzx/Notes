import { useParams } from "react-router-dom";
import { Outlet } from "react-router-dom";


const STU_DATA=[
    {
        id:1,
        name:'李子轩',
        age:'21',
        hobby:'睡觉'
    },
    {
        id:2,
        name:'王天昊',
        age:'20',
        hobby:'打游戏'
    },
    {
        id:3,
        name:'田城烨',
        age:'21',
        hobby:'谈恋爱'
    },
    {
        id:4,
        name:'石浩',
        age:'22',
        hobby:'学习'
    },
    {
        id:5,
        name:'张凯旋',
        age:'22',
        hobby:'爱导管子'
    },
    {
        id:6,
        name:'曹德立',
        age:'22',
        hobby:'学习'
    },
];


const D=()=>{

    const {id}=useParams();
    const [{id:dataID,name,age,hobby}]=STU_DATA.filter(
        (data)=>{
            return data.id===+id;
        }
    );
    console.log(dataID);

    return (
        <>
            <div>
                这是组件D，下面是满足id条件的数据:
                <br />
                {dataID}---
                {name}---
                {age}---
                {hobby}
            </div>
            {/* 在指定组件内部插入Outlet组件，可以使嵌套的路由组件能够正常显示 */}
            <Outlet />
        </>
    );
};

export default D;