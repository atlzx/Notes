

import { useGetInfoQuery } from './store/testApi';



const App=()=>{

    // 使用creatApi自动生成的钩子函数来执行获取数据的操作
    const info=useGetInfoQuery();
    console.log(info);  // 打印得到的数据，可以查看返回的数据格式


    return (
        <div>
            {/* 
                如果isLoading说明正在加载，那么进行提示
                如果isSuccess，再显示出来
            */}
            {info.isLoading&&<div>正在加载中...</div>}
            {info.isSuccess&&<div>{info.data.content}</div>}
        </div>
    );
    
};

export default App;