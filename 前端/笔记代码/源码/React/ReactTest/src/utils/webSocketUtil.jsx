

export const getMessage=(message,toUser,fromUser)=>{

    const obj={};
    obj.message=message;
    obj.toUser=toUser;
    obj.fromUser=fromUser;
    obj.isSystemMessage=0;
    obj.timeStamp=Date.now();
    return JSON.stringify(obj);

};