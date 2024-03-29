
import {createStore} from '../../../../文件/React18/redux.js';

onload=()=>{
    const countReducers=(state={count:0},action)=>{
        switch(action.type){
            case 'ADD':return {...state,count:state.count+1};
            case 'SUB':return {...state,count:state.count-1};
            default:return {...state};
        }
    };
    const store=createStore(countReducers);
    const countElement=document.getElementById("count");
    countElement.textContent=0;
    store.subscribe(
        ()=>{
            countElement.textContent=store.getState().count;
        }
    );
    document.getElementById("subButton").addEventListener(
        "click",
        ()=>{
            store.dispatch({type:'SUB'});
        }
    );
    document.getElementById("addButton").addEventListener(
        "click",
        ()=>{
            store.dispatch({type:'ADD'});
        }
    );
};