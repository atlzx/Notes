import { useContext } from "react";
import BackDrop from "../BackDrop/BackDrop";
import classes from './Confirm.module.css'
import ShopCartContext from "../../../Context/ShopCartContext";


const Confirm=(props)=>{

    const context=useContext(ShopCartContext);
    // 点击取消按钮时的函数
    const cancel=(event)=>{
        event.stopPropagation();
        props.setTipState(false);
    };
    // 要传给backDrop组件，不让其展示的函数
    const notShow=()=>{
        props.setTipState(false);
    };

    const clearHandler=()=>{
        context.shopCartDispatcher({mode:'CLEAR',meal:props.meal,shopCart:context.shopCart,mealData:context.mealData});
    };


    return (
        <BackDrop className={classes.adjust} notShow={notShow}>
            <div className={classes.confirmContainer}>
                <div className={classes.tip}>确认是否清空购物车</div>
                <div className={classes.buttonContainer}>
                    <button className={classes.cancelButton} onClick={cancel}>取消</button>
                    <button className={classes.confirmButton} onClick={clearHandler}>确认</button>
                </div>
            </div>
        </BackDrop>
    );
};

export default Confirm;