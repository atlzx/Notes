

import { getAxiosTemplate } from '../utils/axiosUtil';
import { useNavigate } from 'react-router';
import {message} from 'antd';
import { useContext } from 'react';
import UserContext from '../context/UserContext';
import { setJwt } from '../utils/jwtUtil';

const Login = () => {

    const navigate=useNavigate();
    const userContext=useContext(UserContext);
    
    const submitHandler=(e)=>{
        e.preventDefault();

        const instance=getAxiosTemplate();
        instance.request(
            {
                url:'/login',
                data:{
                    userName:e.target['0'].value,
                    password:e.target['1'].value
                }
            }
        ).then(
            ({data})=>{
                if(data.statusCode===200){
                    setJwt(data.jwt);
                    message.success(
                        {
                            content:'登录'
                        }
                    );
                    console.log(data);
                    userContext.userStatus=data.respData;
                    navigate('/chat');
                }else{
                    message.info(
                        {content:data.message}
                    );
                }
            }
        );
    };

    return (
        <div>
            <form onSubmit={submitHandler}>
                <div>
                    <span>登录:</span>
                    <input type="text" name='loginName'/>
                </div>
                <div>
                    <span>密码:</span>
                    <input type="password" name='password'/>
                </div>
                <div>
                    <button>登录</button>
                    {/* <button 
                        type='button' 
                        onClick={()=>{axios(getAxiosTemplate('/login')).then(({data})=>console.log(data))}} 
                    >
                        测试按钮
                    </button> */}
                </div>
            </form>
        </div>
    );
};

export default Login;
