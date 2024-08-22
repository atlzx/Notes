

import classes from './css/Chat.module.css';
import { useEffect,useContext, useRef, useState} from 'react';
import UserContext from '../context/UserContext';
import {useLoaderData, useNavigate } from 'react-router-dom';
import { getAxiosTemplate } from '../utils/axiosUtil';
import {getMessage} from '../utils/webSocketUtil';


const Chat=()=>{
    const data=useLoaderData();
    const userContext=useContext(UserContext);
    console.log(userContext);
    userContext.userStatus=data.respData;
    // if(!userContext.userStatus){
    //     userContext.userStatus=loaderData;
    // }
    const [friendList,setFriendList]=useState([]);
    const [chatHistory,setChatHistory]=useState([]);
    const [message,setMessage]=useState('');
    const {current}=useRef(
        {
            prevFriend:undefined,
            chatHistory:{},
            chat:{}
        }
    );
    const chatBoxRef=useRef(null);
    const nav=useNavigate();
    console.log(chatHistory);

    const friendClickHandler=(e)=>{
        console.log(e.target.id);
        console.log(e.target.textContent);
        console.log(current);

        
        e.target.style.backgroundColor='green';
        if(current.prevFriend!==e.target&&current.prevFriend!==undefined){
            current.prevFriend.style.backgroundColor='rgba(255, 255, 255, 0.784)';
        }
        current.prevFriend=e.target;
        loadChatHistory(current.prevFriend.id);
        // const userId=userContext.userStatus.userId;
        // const targetId=e.target.id;
        // let url='chat/user/';
        // if(userId<targetId){
        //     url+=userId+'/'+targetId;
        // }else{
        //     url+=targetId+'/'+userId;
        // }
    };

    useEffect(
        ()=>{
            const initParam=async ()=>{
                let resp=null;
                const axiosInstance=getAxiosTemplate();
                
                // 读取朋友列表
                resp=await axiosInstance.request(
                    {
                        url:'/chat/friends',
                        data:{...userContext.userStatus}
                    }
                );
                const arr=resp.data.respData.map(
                    (item)=>{
                        return (
                            <li onClick={friendClickHandler} key={item.userId} id={item.userId}>
                                {item.userName}
                            </li>
                        );
                    }
                );
                console.log(arr);
                setFriendList(arr);
                relink(true);
            };
            initParam();
        },
        []
    );

    useEffect(
        ()=>{
            console.log(chatBoxRef);
            chatBoxRef.current.scrollTop=chatBoxRef.current.scrollHeight;
        },
        [chatHistory]
    );

    const messageSubmitHandler=(e)=>{
        e.preventDefault();
        sendMessage();
    };

    const sendMessage=()=>{
        console.log(current);
        if(current.prevFriend===undefined){
            console.log('还未选择聊天对象');
            return;
        }else if(current.webSocket.readyState!==1){
            console.log('连接异常，需要重新连接');
        }
        if(message===''){
            console.log('消息不能为空');
            return;
        }
        const prevMessage=getMessage(message,current.prevFriend.id,userContext.userStatus.userId)
        current.chat.prevMessage=JSON.parse(prevMessage);
        current.webSocket.send(prevMessage);
    };

    const relink=(isInit)=>{
        if(!isInit&&current.webSocket!==undefined&&current.webSocket.readyState===1){
            console.log('还在连接中');
            return;
        }
        current.webSocket=new WebSocket(`ws://8.130.44.112:8080/chat/${userContext.userStatus.userId}`);
        current.webSocket.onopen=()=>{
            console.log('客户端连接成功');
        };
        current.webSocket.onmessage=({data})=>{
            console.log(`收到信息:${data}`);
            const obj=JSON.parse(data);
            console.log(obj);
            updateChatHistoty(obj);
        };

        current.webSocket.onclose=()=>{
            console.log('连接关闭了');
        }
    };

    const updateChatHistoty=(obj)=>{
        if(obj.isSystemMessage){
            console.log(`系统消息:${obj.message}`);
            if(obj.message==='success'){
                console.log('消息发送成功');
                setMessage('');
                // 消息发送成功后，更新本用户发给其他用户的聊天记录
                const prevMessage=current.chat.prevMessage;
                if(!current.chatHistory[`${prevMessage.toUser}`]){
                    current.chatHistory[`${prevMessage.toUser}`]=[];
                }
                current.chatHistory[`${prevMessage.toUser}`].push({to:prevMessage.message,type:'to',timeStamp:obj.timeStamp,message:prevMessage.message});
            }
        }else{
            // 收到他人发来的消息时，更新聊天记录
            if(obj.fromUser===userContext.userStatus.userId){
                if(current.chatHistory[`${obj.toUser}`]===undefined){
                    current.chatHistory[`${obj.toUser}`]=[];
                }
                current.chatHistory[`${obj.toUser}`].push({to:obj.message,type:'to',timeStamp:obj.timeStamp,message:obj.message});
            }else{
                if(current.chatHistory[`${obj.fromUser}`]===undefined){
                    current.chatHistory[`${obj.fromUser}`]=[];
                }
                current.chatHistory[`${obj.fromUser}`].push({from:obj.message,type:'from',timeStamp:obj.timeStamp,message:obj.message});
            }
            console.log(current);
        }
        // 根据情况更新聊天记录
        if(current.prevFriend){
            console.log(current.prevFriend.id);
            loadChatHistory(current.prevFriend.id);
        }
    };

    const loadChatHistory=(id)=>{
        if(current.chatHistory[`${id}`]){
            const arr=current.chatHistory[`${id}`].map(
                (item)=>{
                    console.log(item);
                    return (
                        <div className={item.type==='to'?classes.to:classes.from} key={item.timeStamp}>{item[item.type]}</div>
                    );
                }
            );
            setChatHistory(arr);
        }else{
            setChatHistory([]);
        }
    };

    const keyDownHandler=(e)=>{
        if(e.key==='Enter'){
            e.preventDefault();
            sendMessage();
        }
    };




    

    return (
        <>
            <div>
                <button type='button' onClick={()=>nav('/login')}>返回</button>
                <button type='button' onClick={relink}>重新连接</button>
            </div>
            <div className={classes.chatBody}>
                <div className={classes.status}>
                    {`${userContext.userStatus.userName}`}

                </div>
                <div className={classes.chatContainer}>
                    <div className={classes.friendListContainer}>
                        <ul className={classes.friendList}>
                            {friendList}
                        </ul>
                    </div>
                    <div className={classes.chatBox}>
                        <div className={classes.chatHistory} ref={chatBoxRef}>{chatHistory}</div>
                        <div className={classes.chatInputContainer}>
                            <form onSubmit={messageSubmitHandler}>
                                <div className={classes.chatInput}>
                                    <textarea 
                                        name="message" 
                                        id="" 
                                        className={classes.chatTextArea}
                                        onChange={({target:{value}})=>setMessage(value)}
                                        onKeyDown={keyDownHandler}
                                        value={message}
                                    ></textarea>
                                </div>
                                <div className={classes.chatButtonContainer}>
                                    <button className={classes.sendButton}>发送</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
        </>

    );
};

export default Chat;