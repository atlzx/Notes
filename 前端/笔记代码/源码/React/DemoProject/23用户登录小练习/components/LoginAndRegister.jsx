
import { useState } from 'react';
import classes from './App.module.css';
import { useDispatch, useSelector } from 'react-redux';
import { login,register } from '../store/reducers/userReducer';
import { useLocation, useNavigate } from 'react-router-dom';

const LoginAndRegister=()=>{

    const [formLogin,setFormLogin]=useState(true);
    const [error,setError]=useState(
        {
            isError:false,
            info:''
        }
    );

    const dispatch=useDispatch();
    const nav=useNavigate();
    // 使用解构，最后得到的是location对象的state属性内的prevLocation属性,也就是Check经过校验后传递过来的属性
    const location=useLocation();
    const {isLogin}=useSelector(
        (state)=>{
            return state.user;
        }
    );

    // 用于进行注册和登录校验的函数
    // 由于并没有实现相对应的后端，因此此处简单的模拟一下
    const submitHandler=(e)=>{
        e.preventDefault();
        if(userName==='lzx'&&pwd==='123456'&&formLogin===true){
            dispatch(login({userName}));  // 登录成功时，调用dispatch修改state值
            // 使用location来重定向到之前的记录中所想访问的路径，而不是无论从哪里登录都会重定向到一个指定的页面
            // ?.是ES6语法的一个新特性，它用来专门判断多层调用时中途可能会出现的不存在现象
            // 当使用 ?. 进行判断时，会先判断前面的属性是否存在，如果存在再进行下面的操作
            // 如果不存在，如下所示，使用 || 运算符搭配进行运算，从而处理该属性不存在的情况
            nav(location.state?.prevLocation?.pathname||"/",{replace:true});
        }else if(userName.length>0&&pwd.length>0&&email.length>0&&formLogin===false){
            dispatch(register({userName}));  // 注册成功时，调用dispatch修改state值
            setFormLogin(true);
            setEmail('');
            setUserName('');
            setPwd('');
        }else if(formLogin===true){
            setError(
                {
                    isError:true,
                    info:'账户名或密码错误'
                }
            );
        }else if(formLogin===false){
            setError(
                {
                    isError:true,
                    info:'注册所用的格式不正确'
                }
            );
        }
    };

    const [userName,setUserName]=useState('');
    const [pwd,setPwd]=useState('');
    const [email,setEmail]=useState('');

    // 该函数用于转换注册和登录界面
    const convertState=()=>{
        setFormLogin(
            (prevState)=>{
                return !prevState;
            }
        );
        setEmail('');
        setPwd('');
        setUserName('');
        setError(
            {
                isError:false,
                info:''
            }
        );
    };

    const userNameInputChangeHandler=(e)=>{
        setUserName(e.target.value);
    };
    const pwdChangeHandler=(e)=>{
        setPwd(e.target.value);
    };
    const emailInputChangeHandler=(e)=>{
        setEmail(e.target.value);
    };

    return (
        isLogin
            ?
        (<><p>您已登录</p></>):
        (
        <>
            <div>
                <h1>{formLogin?'登录':'注册'}</h1>
                <form onSubmit={submitHandler}>
                    <div className={classes.generalContainer}>
                        <span>用户名:</span>
                        <input type="text" value={userName} onChange={userNameInputChangeHandler}/>
                    </div>
                    <div className={classes.generalContainer}>
                        <span>密码:</span>
                        <input type="password" value={pwd} onChange={pwdChangeHandler}/>
                    </div>
                    {
                        !formLogin
                            &&
                        <div className={classes.generalContainer}>
                            <span>邮箱</span>
                            <input type="email" value={email} onChange={emailInputChangeHandler}/>
                        </div>
                    }
                    {
                        error.isError
                            &&
                        <div>
                            <p style={{color:'red'}}>{error.info}</p>
                        </div>
                    }
                    <div className={classes.buttonContainer}>
                        <button>{formLogin?'登录':'注册'}</button>
                        <button type='button' onClick={convertState}>{formLogin?'还未注册?去注册':'去登录'}</button>
                    </div>
                </form>
            </div>
        </>
        )

    );

};

export default LoginAndRegister;