
import classes from './SettlementDetail.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import Meal from '../Meals/Meal/Meal';
import { useContext, useState } from 'react';
import ShopCartContext from '../../Context/ShopCartContext';
import BackDrop from '../UI/BackDrop/BackDrop';
import Confirm from '../UI/Confirm/Confirm';

const SettlementDetail=(props)=>{

    const context=useContext(ShopCartContext);
    const [tipState,setTipState]=useState(false);

    const arr=context.shopCart.shop.map(
        (item)=>{
            return (
                <Meal key={item.id} mealData={item} showDesc={false}></Meal>
            );
        }
    );

    // 阻止事件冒泡的函数
    const noPropagetion=(event)=>{
        event.stopPropagation();
    }
    // 展示确认框的函数
    const showTip=()=>{
        setTipState(true);
    }
    



    return (
        <>
            <BackDrop notShow={props.notShowSettleDetail}>
                <div className={classes.settlementDetailContainer} onClick={noPropagetion}>
                    <div className={classes.operationContainer}>
                        <span className={classes.detailTitle}>商品详情</span>
                        <div className={classes.titleContainer} onClick={showTip}>
                            <FontAwesomeIcon icon={faTrash} className={classes.trashIcon}></FontAwesomeIcon>
                            <span className={classes.clearDesc}>清空购物车</span>
                        </div>
                    </div>
                    <div>
                        {arr}
                    </div>
                </div>
            </BackDrop>
            {/* 下面是点击清空购物车时弹出的确认框 */}
            {tipState&&<Confirm setTipState={setTipState}></Confirm>}
        </>
    );
};

export default SettlementDetail;