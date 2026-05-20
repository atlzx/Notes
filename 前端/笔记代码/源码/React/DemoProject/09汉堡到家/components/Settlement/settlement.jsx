import { useContext} from 'react';
import classes from './settlement.module.css'
import ShopCartContext from '../../Context/ShopCartContext';

const Settlement=(props)=>{
    // 得到context对象
    const context=useContext(ShopCartContext);

    const disbleState=!context.shopCart.totalAccount;

    const clickHandler=(event)=>{
        event.stopPropagation();
        // 在确认已经选了东西的前提下再进行渲染，避免睿智用户不选东西瞎点导致多次渲染影响性能
        if(context.shopCart.totalAccount>0){
            props.setToPayState(true);
        }
    };
    

    return (
        <div className={classes.settlementContainer} onClick={props.showSettlementDetail}>
            <div className={classes.imgContainer}>
                {/* 使用绝对路径导入图片 */}
                <img src="/src/assets/bag.png" alt="小箱子" className={classes.imgIcon}/>
                {!disbleState&&<span className={classes.count}>{context.shopCart.totalAccount}</span>}
            </div>
            <div className={classes.settlementButtonContainer}>
                {!disbleState?<p className={classes.price}>{context.shopCart.totalPrice}</p>:<p className={classes.noMeal}>未选购商品</p>}
                
                <button className={`${classes.settlementButton} ${disbleState&&classes.disableButton}`} onClick={clickHandler}>去结算</button>
            </div>
        </div>
    );
};

export default Settlement;