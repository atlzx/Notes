import React from "react"

const ShopCartContext=React.createContext(
    {
        shop:[],
        totalPrice:0,
        totalAccount:0,
        addMeal:()=>{},
        decreaseMeal:()=>{}
    }
);

export default ShopCartContext;