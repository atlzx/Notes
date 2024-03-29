import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import classes from './Search.module.css';
import { useEffect, useState } from "react";


const Search=(props)=>{

    const [keyWordState,setKeyWordState]=useState('');

    const search=(event)=>{
        setKeyWordState(event.target.value.trim());
    };


    // 使用useEffect来减少请求次数
    // 该useEffect会在setState方法执行后执行，因为只有setState方法调用后，useEffect才会监测到state变量的变化
    useEffect(
        ()=>{
            // 每检测到keyWordState发生变化，就生成一个定时器，在1秒后执行查询操作
            const timer=setTimeout(
                ()=>{
                    console.log(keyWordState);
                    props.searchMeal(keyWordState);
                },
                1000
            );
            // 回调函数的返回值负责清除上一次useEffect的回调函数的副作用，因此它也被称为useEffect的清除函数
            // 该函数会在下一次useEffect的回调函数执行之前执行
            return ()=>{
                // 回调函数每调用一次，就清除上一次回调函数定义的定时器，只要清除时还没到1s，就可以达到减少请求次数的目的
                clearTimeout(timer);
            }
        },
        [keyWordState]
    );



    return (
        <div className={classes.searchContainer}>
            <div className={classes.inputContainer}>
                <input type="text" className={classes.searchInput} placeholder="请输入关键字" onChange={search} value={keyWordState}/>
                <FontAwesomeIcon icon={faSearch} className={classes.searchIcon}></FontAwesomeIcon>
            </div>
        </div>
    );
};

export default Search;