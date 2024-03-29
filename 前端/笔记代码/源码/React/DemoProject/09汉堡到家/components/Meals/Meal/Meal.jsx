import classes from './Meal.module.css';
import Counter from '../../UI/Counter/Counter';


const Meal=(props)=>{

    const mealData=props.mealData;

    return (
        <div className={classes.Meal}>
            <div>
                <img src={mealData.img} alt="汉堡图片" />
            </div>
            <div className={classes.information}>
                <h3 className={classes.title}>{mealData.title}</h3>
                {props.showMeal&&<p className={classes.desc}>{mealData.desc}</p>}
                <div className={classes.priceContainer}>
                    <span className={classes.price}>{mealData.price}</span>
                    <Counter meal={mealData}></Counter>
                </div>
            </div>
        </div>
    );
};

export default Meal;