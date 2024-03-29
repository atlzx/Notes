import classes from './ToPay.module.css'
import ShopCartContext from '../../Context/ShopCartContext';
import { useContext } from 'react';
import Counter from '../UI/Counter/Counter';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';


const ToPay=(props)=>{

    const context=useContext(ShopCartContext);

    // 动态生成数组
    const mealInfo=context.shopCart.shop.map(
        (item)=>{
            return (
                <div key={item.id} className={classes.mealContainer}>
                    <div className={classes.imgContainer}>
                        <img src={item.img} alt={item.desc} />
                    </div>
                    <div className={classes.titleContainer}>
                        <div className={classes.title}>{item.title}</div>
                        <div className={classes.operate}>
                            <Counter meal={item}></Counter>
                            <span className={classes.price}>{item.count*item.price}</span>
                        </div>
                    </div>
                </div>
            );
        }
    );

    // 遍历数组计算总价
    let totalPrice=0;
    for(let i of context.shopCart.shop){
        totalPrice+=i.count*i.price;
    }

    // 点击叉子后隐藏该界面的函数
    const hideToPayState=(event)=>{
        event.stopPropagation();
        props.setToPayState(false);
    }

    return (
        <div className={classes.toPayContainer}>
            <FontAwesomeIcon icon={faXmark} className={classes.closeButton} onClick={hideToPayState}></FontAwesomeIcon>
            <div className={classes.mealInfoContainer}>
                <div className={classes.title}>餐品详情</div>
                <div className={classes.mealInfo}>
                    {mealInfo}
                </div>
                <div className={classes.totalContainer}>
                    <span className={classes.totalPriceNum}>{totalPrice}</span>
                    <span className={classes.totalPrice}>合计:</span>
                </div>
                <div className={classes.leftCircle}></div><div className={classes.rightCircle}></div>
            </div>
            <div className={classes.goPayContainer}>
                <span className={classes.allTotal}>合计:{totalPrice}</span>
                <button className={classes.toPayButton}>去支付</button>
            </div>
        </div>
    );
};

export default ToPay;