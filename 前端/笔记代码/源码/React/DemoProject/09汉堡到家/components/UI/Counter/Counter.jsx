import classes from './Counter.module.css'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPlus,faMinus} from '@fortawesome/free-solid-svg-icons'
import ShopCartContext from '../../../Context/ShopCartContext';
import { useContext } from 'react';


const Counter=(props)=>{

    // 得到context
    const context=useContext(ShopCartContext);
    const mealData=props.meal;

    // 点击增加按钮后调用的方法
    const addMeal=(event)=>{
        event.stopPropagation();  // 阻止事件向上冒泡，避免触发backDrop的点击事件隐藏详细信息
        context.shopCartDispatcher({mode:'ADD',meal:props.meal,shopCart:context.shopCart});
    }

    // 点击减少按钮后调用的方法
    const decreaseMeal=(event)=>{
        event.stopPropagation();  // 阻止事件向上冒泡，避免触发backDrop的点击事件隐藏详细信息
        context.shopCartDispatcher({mode:'REMOVE',meal:props.meal,shopCart:context.shopCart});
    }



    return (
        <div className={classes.countContainer}>
            {/* 判断mealData.count是否为NaN或0，如果不是再显示减按钮和当前购买数量 */}
            {mealData.count!==0&&!isNaN(mealData.count)?<><button className={classes.decrease} onClick={decreaseMeal}><FontAwesomeIcon icon={faMinus}></FontAwesomeIcon></button><span className={classes.count}>{mealData.count}</span></>:<></>}
            <button onClick={addMeal} className={classes.add}>
                <FontAwesomeIcon icon={faPlus}></FontAwesomeIcon>
            </button>
        </div>
    );
};

export default Counter;