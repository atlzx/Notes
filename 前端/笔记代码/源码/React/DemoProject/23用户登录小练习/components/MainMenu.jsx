
import { useDispatch, useSelector } from 'react-redux';
import {Link} from 'react-router-dom';
import { logOut } from '../store/reducers/userReducer';

const MainMenu=()=>{

    const {isLogin,userName}=useSelector(
        (state)=>{
            return state.user;
        }
    );

    const dispatch=useDispatch();

    return (
        <ul>
            <li><Link to={'/'}>首页</Link></li>
            <li><Link to={'/login'}>{isLogin?userName:'登录/注册'}</Link></li>
            {
                isLogin
                    &&
                (
                    <>
                        <li><Link to={'/info'}>用户信息</Link></li>
                        <li onClick={()=>{dispatch(logOut())}}><Link to={'/login'}>登出</Link></li>
                    </>
                )
            }

        </ul>
    );
};

export default MainMenu;