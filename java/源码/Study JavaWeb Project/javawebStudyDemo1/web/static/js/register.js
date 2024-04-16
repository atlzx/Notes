onload=function(){
    let userNameInput=document.getElementById("userNameInput");
    let passwordInput=document.getElementById("passwordInput");
    let confirmPasswordInput=document.getElementById("confirmPasswordInput");
    let userNameTip=document.getElementById("userNameTip");
    let passwordTip=document.getElementById("passwordTip");
    let confirmPasswordTip=document.getElementById("confirmPasswordTip");
    let regexUserName=/^[A-Za-z0-9]{5,10}$/;
    let regexPassword=/^[0-9]{6}$/
    let submitButton=document.getElementById("submitButton");
    let loginButton=document.getElementById("loginButton");
    function judgeUserName(target){
        // console.log(target);
        // console.log(target.value);
        if(regexUserName.test(target.value)){
            userNameTip.textContent="OK";
        }else{
            userNameTip.textContent="5~10位的字母和数字";
        }
    }
    function judgePassword(target){
        if(regexPassword.test(target.value)){
            passwordTip.textContent="OK";
        }else{
            passwordTip.textContent="密码必须为6位数字";
        }
    }
    function judgeConfirmPassword(target){
        if(target.value===passwordInput.value){
            confirmPasswordTip.textContent="OK";
        }else{
            confirmPasswordTip.textContent="两次输入的密码不一致";
        }
    }
    userNameInput.addEventListener(
        "keyup",
        (event)=>{
            judgeUserName(event.target);
        }
    );
    passwordInput.addEventListener(
        "keyup",
        (event)=>{
            judgePassword(event.target);
            judgeConfirmPassword(confirmPasswordInput);
        }
    );
    confirmPasswordInput.addEventListener(
        "keyup",
        (event)=>{
            judgeConfirmPassword(event.target);

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
    loginButton.addEventListener(
        "click",
        (event)=>{
            location="./login.html";
        }
    );
};