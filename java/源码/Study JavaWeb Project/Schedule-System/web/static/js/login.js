onload=function(){
    let userNameInput=document.getElementById("userNameInput");
    let passwordInput=document.getElementById("passwordInput");
    let userNameTip=document.getElementById("userNameTip");
    let passwordTip=document.getElementById("passwordTip");
    let regexUserName=/^[A-Za-z0-9]{5,10}$/;
    let regexPassword=/^[0-9]{6}$/
    let submitButton=document.getElementById("submitButton");
    let registerButton=document.getElementById("registerButton");
    function judgeUserName(target){
        console.log(target);
        console.log(target.value);
        return (regexUserName.test(target.value));
    }
    function judgePassword(target){
        return (regexPassword.test(target.value));
    }
    userNameInput.addEventListener(
        "keyup",
        (event)=>{
            if(judgeUserName(event.target)){
                userNameTip.textContent="OK";
            }else{
                userNameTip.textContent="5~10位的字母和数字";
            }
        }
    );
    passwordInput.addEventListener(
        "keyup",
        (event)=>{
            if(judgePassword(event.target)){
                passwordTip.textContent="OK";
            }else{
                passwordTip.textContent="密码必须为6位数字";
            }
        }
    );
    submitButton.addEventListener(
        "click",
        (event)=>{
            if(!(judgeUserName(userNameInput)&&judgePassword(passwordInput))){
                event.preventDefault(); // 阻止提交
                userNameTip.textContent="5~10位的字母和数字";
                passwordTip.textContent="密码必须为6位数字";
            }
        }
    );
    registerButton.addEventListener(
        "click",
        (event)=>{
            location="./register.html";
        }
    );
};