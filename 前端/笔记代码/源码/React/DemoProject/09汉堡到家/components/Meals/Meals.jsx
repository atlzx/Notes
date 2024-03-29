import Meal from './Meal/Meal'
import classes from './Meals.module.css'

const Meals=(props)=>{

    const mealData=props.mealData;

    // 处理得到的数据，得到批量组件
    const mealArr=mealData.map(
        (item)=>{
            return (
                <Meal key={item.id} mealData={item} showMeal={true}></Meal>
            );
        }
    );


    return (
        <div className={classes.Meals}>
            {mealArr}
        </div>
    );
};

export default Meals;