
const FiltSelect=(props)=>{

    const selectChangeHandler=(event)=>{
        console.log('函数执行');
        props.setYear(
            ()=>{
                return +event.target.value;
            }
        );
    };


    return (
        <div>
            <span>年份:</span>
            <select name="" id="" value={props.year} onChange={selectChangeHandler}>
                <option value="2020">2020</option>
                <option value="2021">2021</option>
                <option value="2022">2022</option>
            </select>
        </div>
    );
};

export default FiltSelect;