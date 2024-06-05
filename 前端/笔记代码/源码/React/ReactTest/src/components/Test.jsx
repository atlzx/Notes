
import axios from 'axios';
import { useState } from 'react';

const Test = () => {
    const [imgUrl,setImgUrl]=useState(null);

    const changeHandler=(e)=>{
        const file=e.target.files[0];
        

        // 发起一个post请求
        axios(
            {
                method: 'post',
                url: 'http://localhost:8080/fileUpload',
                data: {
                    file:file,
                },
                headers:{
                    "Content-Type":'multipart/form-data'
                }
            }
        ).then(
            ({data})=>{
                console.log(data.result);
                if(data.result!==null){
                    setImgUrl(data.result);
                }
            }
        )
    };
    return (
        <>
            <form>
                <input type="file" onChange={changeHandler}/>
            </form>
            {imgUrl!==null&&<img src={imgUrl} alt="上传图像显示" />}
        </>

    );
};

export default Test;
