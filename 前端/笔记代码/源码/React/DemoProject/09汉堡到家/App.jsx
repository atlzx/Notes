import {useEffect, useReducer, useState } from 'react';
import Meals from './components/Meals/Meals'
import ShopCartContext from './Context/ShopCartContext';
import Search from './components/Search/Search';
import Settlement from './components/Settlement/settlement';
import SettlementDetail from './components/Settlement/SettlementDetail';
import ToPay from './components/Settlement/ToPay';


// 定义静态变量，以模拟后端返回数据
const MEAL_DATA=[
    {
        id: '1',
        title: '汉堡包',
        desc: '百分百纯牛肉配搭爽脆酸瓜洋葱粒与美味番茄酱经典滋味让你无法抵挡！',
        price: 12,
        img: '/img/meal/1.png'  // img设置为绝对路径，以防数组在传递过程中其相对路径一直变化
    },
    {
        id: '2',
        title: '双层吉士汉堡',
        desc: '百分百纯牛肉与双层香软芝，加上松软面包及美味酱料，诱惑无人能挡！',
        price: 20,
        img: '/img/meal/2.png'
    },
    {
        id: '3',
        title: '巨无霸',
        desc: '两块百分百纯牛肉，搭配生菜、洋葱等新鲜食材，口感丰富，极致美味！',
        price: 24,
        img: '/img/meal/3.png'
    }, 
    {
        id: '4',
        title: '麦辣鸡腿汉堡',
        desc: '金黄脆辣的外皮，鲜嫩幼滑的鸡腿肉，多重滋味，一次打动您挑剔的味蕾！',
        price: 21,
        img: '/img/meal/4.png'
    }, 
    {
        id: '5',
        title: '板烧鸡腿堡',
        desc: '原块去骨鸡排嫩滑多汁，与翠绿新鲜的生菜和香浓烧鸡酱搭配，口感丰富！',
        price: 22,
        img: '/img/meal/5.png'
    }, 
    {
        id: '6',
        title: '麦香鸡',
        desc: '清脆爽口的生菜，金黄酥脆的鸡肉。营养配搭，好滋味的健康选择！',
        price: 14,
        img: '/img/meal/6.png'
    }, 
    {
        id: '7',
        title: '吉士汉堡包',
        desc: '百分百纯牛肉与香软芝士融为一体配合美味番茄醬丰富口感一咬即刻涌现！',
        price: 12,
        img: '/img/meal/7.png'
    }
];

// 定义购物车操作的reducer
const reducer=(prevState,action)=>{
    const newShopCart={...action.shopCart};
    switch(action.mode){
        case 'ADD':
            // 如果购物车中找不到该项，那么说明该meal是新点的，需要向shop中添加该meal
            if(newShopCart.shop.indexOf(action.meal)===-1){
                newShopCart.shop.push(action.meal);
                action.meal.count=1;
            }else{
                action.meal.count+=1;  // 添加当前汉堡的购买数量
            }
            // 更新总数和总价
            newShopCart.totalAccount++;
            newShopCart.totalPrice+=action.meal.price;
            return newShopCart;
        case 'REMOVE':
            // 更新总数和总价
            newShopCart.totalAccount--;
            newShopCart.totalPrice-=action.meal.price;
            // 让meal.count减一，然后判断是否减到0
            action.meal.count-=1;
            // 如果该汉堡的购买数量减到0，那么直接将该项从购物车中移除
            if(action.meal.count===0){
                newShopCart.shop.splice(newShopCart.shop.indexOf(action.meal),1);  // 取得对应的索引删除
            }
            return newShopCart;
        case 'CLEAR':
            action.mealData.forEach(
                (item)=>{
                    delete item.count;
                }
            );
            return {
                shop:[],
                totalPrice:0,
                totalAccount:0,
                addMeal:()=>{},
                decreaseMeal:()=>{}
            };
        default:return prevState;
    }
}


const App=()=>{
    const [mealData,setMealData]=useState(MEAL_DATA);  // 得到用于筛选的state值
    /*
    const [shopCart,setShopCart]=useState(
        {
            shop:[],
            totalPrice:0,
            totalAccount:0
        }
    );  // 得到购物车的state值
    */
    const [settlementDetailState,setSettlementDetailState]=useState(false);  // 得到state用以表示购物车细节是否显示
    const [toPayState,setToPayState]=useState(false);
    // 这是使用useReducer替换原来的老的useState的整合性写法，以前的写法依旧保留于注释内
    // 由于老式的写法让Context中的value记录的是ShopCart的三个具体属性而不是shopCart这一个对象，再加上用useReducer重写的时候已经过去一段时间了
    // 因此可能会有bug
    const [shopCart,shopCartDispatcher]=useReducer(
        reducer,
        {
            shop:[],
            totalPrice:0,
            totalAccount:0
        }
    );





    
/*
    // addMeal用于在用户点击增加按钮时向购物车中添加食物信息，并更新购物车
    const addMeal=(meal)=>{
        // 浅拷贝对象
        const newShopCart={...shopCart};
        // 如果购物车中找不到该项，那么说明该meal是新点的，需要向shop中添加该meal
        if(newShopCart.shop.indexOf(meal)===-1){
            newShopCart.shop.push(meal);
            meal.count=1;
        }else{
            meal.count+=1;  // 添加当前汉堡的购买数量
        }
        // 更新总数和总价
        newShopCart.totalAccount++;
        newShopCart.totalPrice+=meal.price;
        // 不建议在严格模式下(React.strictMode)把更改数值的操作写在setShopCart里，因为这操蛋玩意会执行里面的代码两次
        setShopCart(newShopCart);
    };
*/
/*
    // decreaseMeal用于用户在点击减少按钮时向购物车中减少食物信息，并更新购物车
    // 无需考虑点击到负数的情况，因为点到0，-按钮会自动消失
    const decreaseMeal=(meal)=>{
        const newShopCart={...shopCart};
        // 更新总数和总价
        newShopCart.totalAccount--;
        newShopCart.totalPrice-=meal.price;
        // 让meal.count减一，然后判断是否减到0
        meal.count-=1;
        // 如果该汉堡的购买数量减到0，那么直接将该项从购物车中移除
        if(meal.count===0){
            newShopCart.shop.splice(newShopCart.shop.indexOf(meal),1);  // 取得对应的索引删除
        }
        setShopCart(newShopCart);
        // 在减少至newShopCart的totalAccount到0后，取消backDrop和购物车详细信息的显示
        // 由于state的setxxx函数都是异步的，后面的代码依旧会执行
    };
*/
/*
    // clearMeal用于清空购物车，它会被放进context中
    const clearMeal=()=>{
        let newShopCart={
            shop:[],
            totalPrice:0,
            totalAccount:0,
            addMeal:()=>{},
            decreaseMeal:()=>{}
        };
        mealData.forEach(
            (item)=>{
                delete item.count;
            }
        );
        setShopCart(newShopCart);
        setSettlementDetailState(false);
    };
*/

    // 进行过滤(搜索)操作的函数
    const searchMeal=(key)=>{
        // 得到通过用户输入的key过滤后的数组
        const filterData=MEAL_DATA.filter(
            (item)=>{
                if(item.title.indexOf(key)>=0){
                    return true;
                }
                return false;
            }
        );
        setMealData(filterData);
    };


    // toggleSettlementDetail函数用来设置购物车详细信息是否显示,它作用于用户点击购物车栏时
    const toggleSettlementDetail=(event)=>{
        event.stopPropagation();
        setSettlementDetailState(
            (prevStatus)=>{
                if(shopCart.totalAccount>0){
                    return !prevStatus;
                }else{
                    return false;
                }
            }
        );
    };

    // 不显示购物车细节的函数，用于用户看到购物车详细信息后,点击backDrop时取消查看购物车细节
    const notShowSettleDetail=()=>{
        setSettlementDetailState(false);
    };

    useEffect(
        ()=>{
            if(shopCart.totalAccount===0){
                setSettlementDetailState(false);
                setToPayState(false);
            }
        }
    );
    

    return (
        /* 使用context.provider包裹子组件，可以使子组件及其之下的全部组件读取context时读取的是provider的value属性重新设置的值 */
        // 该value是使用useReducer重写后一并重写的value值
        // 之前的value值是: <ShopCartContext.Provider value={{shop,totalAccount,totalPrice,addMeal,clearMeal}}>
        <ShopCartContext.Provider value={{shopCart,shopCartDispatcher,mealData}}>
            <div>
                <Search searchMeal={searchMeal}></Search>
                <Meals mealData={mealData}></Meals>
                <Settlement showSettlementDetail={toggleSettlementDetail} setToPayState={setToPayState}></Settlement>
                {settlementDetailState&&shopCart.totalAccount&&<SettlementDetail notShowSettleDetail={notShowSettleDetail}></SettlementDetail>}
                {toPayState&&<ToPay setToPayState={setToPayState}></ToPay>}
            </div>
        </ShopCartContext.Provider>
        
    );
};

export default App;