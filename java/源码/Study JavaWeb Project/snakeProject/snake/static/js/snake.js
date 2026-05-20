onload=function(){
    const snake=document.getElementById("snake");
    const snakes=snake.getElementsByTagName("div");  // div
    const food=document.getElementById("food");  // food对象，用来进行坐标转换
    let direction=null;
    // keyArray数组包括可以按的合法的键
    const keyArray=["w","s","a","d","ArrowUp","ArrowDown","ArrowLeft","ArrowRight"];
    const reverseKeyObject={
        w:["ArrowDown","s"],
        s:["ArrowUp","w"],
        a:["ArrowRight","d"],
        d:["ArrowLeft","a"],
        ArrowUp:["ArrowDown","s"],
        ArrowDown:["ArrowUp","w"],
        ArrowLeft:["ArrowRight","d"],
        ArrowRight:["ArrowLeft","a"],
    };  // reverseKeyObject对象承载键对应的相反的键的数组
    let eventCount=0;  // 该变量表示一个定时器时间内用户按键的次数
    let getFood=false;  // 该变量表示蛇是否吃到食物
    let scoreElement=document.getElementById("score");
    let levelElement=this.document.getElementById("level");
    let score=0,level=1;  // 表示当前的分数和等级


    function changeFood(){
        let randX,randY;
        while(true){
            // 食物的Top和Left偏移量区间为0——290，且是10的倍数
            // 因此使用随机数生成0~29的随机数，再令它们乘以10即可得到想要的区间内的数
            randX=Math.floor(Math.random()*30)*10;
            randY=Math.floor(Math.random()*30)*10;
            if(!isConflict(randX,randY)){
                // 如果生成的随机坐标合法再赋值
                food.style.top=randY+"px";  // 不要忘记加px单位
                food.style.left=randX+"px";
                break; // 生成的随机坐标合法，跳出循环
            }
        }
        // console.log(randX,randY);
    }

    // 食物的坐标不应该与蛇的身体坐标重合，因此每次产生随机数时都要进行判断
    // isConflict函数用来判断生成的随机数坐标是否合法，合法返回false,不合法返回true
    function isConflict(X,Y){
        // 遍历蛇身，查看是否重叠
        for(let i of snakes){
            if(X===i.offsetLeft&&Y===i.offsetTop){
                return true;
            }
        }
        return false;
    }


    // updateSnake函数用来更新蛇身，并在更新成功后更新食物位置
    // 它返回true时表示更新成功，返回false表示更新失败
    function updateSnake(head){
        let Xcoordinate,Ycoordinate;
        // 每次移动蛇的身体，相当于将蛇的尾部放置到蛇的头部前
        // 因此只需要每次移动时移动蛇的尾部即可
        // 无论是否吃到食物，蛇每次都需要执行这一步骤,因为它必定会移动
        let tail=snakes[snakes.length-1];
        if(direction==="w"||direction==="ArrowUp"){
            // offsetTop和offsetLeft只返回数值，设置CSS样式时需要手动添加px单位
            Xcoordinate=head.offsetLeft;
            Ycoordinate=head.offsetTop-10;
        }else if(direction==="s"||direction==="ArrowDown"){
            Xcoordinate=head.offsetLeft;
            Ycoordinate=head.offsetTop+10;
        }else if(direction==="a"||direction==="ArrowLeft"){
            Xcoordinate=head.offsetLeft-10;
            Ycoordinate=head.offsetTop;
        }else if(direction==="d"||direction==="ArrowRight"){
            Xcoordinate=head.offsetLeft+10;
            Ycoordinate=head.offsetTop;
        }
        // 在真正更新蛇身前，先判断要更新的蛇头位置是否合法
        // 调用isFail函数判断蛇头是否出界或是否吃到自身
        // 如果该函数返回true,则说明此次更新非法，返回false，表示更新失败
        if(isFail(Xcoordinate,Ycoordinate)){
            return false;
        }
        tail.style.left=Xcoordinate+"px";  // 不要忘记+px单位
        tail.style.top=Ycoordinate+"px";
        // 有于蛇的尾部仅仅在视觉上跑到了头的前边，而HTML结构并没有
        // 因此需要将结构上的尾部也挪到前面
        snake.insertAdjacentElement("afterbegin",tail);
        if(getFood){
            getFood=false;
            score++;  // 每次吃到食物就增加分数
            scoreElement.textContent=''+score;  // 更新网页显示的分数
            if(score%5===0&&level<15){
                level++;
                levelElement.textContent=''+level;  // 更新网页显示的蛇的等级
            }
            changeFood();
        }
        return true;  // 更新完后返回true
    }



    // 判断游戏是否结束，即蛇头是否超出界限或碰到蛇身
    // 该函数会在判定结束后返回true,否则返回false
    function isFail(x,y){
        // 判断蛇头是否出界
        if(x<0||x>290||y<0||y>290){
            return true;
        }
        // 遍历判断是否吃到蛇身
        // i=1是因为必定碰不到蛇头
        // i<snakes.length-1是因为未来的蛇头坐标碰到当前蛇尾是合法的,因为拿未来的蛇头坐标与当前的蛇身坐标相比本来就有些问题
        // 一方面是因为蛇尾会在之后成为蛇头，另一方面也是因为未来的蛇尾在蛇头到达该位置后实际上也会挪动一格，而我们比较的是未挪动的蛇尾坐标
        for(let i=1;i<snakes.length-1;i++){
            if(x===snakes[i].offsetLeft&&y===snakes[i].offsetTop){
                return true;
            }
        }
        return false;
    }

    function isEatFood(head){
        // 先判断蛇有没有吃到食物
        // 如果吃到食物，给蛇添加一个div标签在最后，并更新事物的位置
        if(head.offsetLeft===food.offsetLeft&&head.offsetTop===food.offsetTop){
            snake.insertAdjacentHTML("beforeend","<div></div>");
            // changeFood();
            return true;
        }
        return false;
    }


    changeFood();

    // 监听键盘事件
    this.document.addEventListener(
        "keydown",
        (event)=>{
            // 当按下的键为指定键时才改变direction
            // 这是为了防止用户按其他键时蛇停止移动
            // 使用eventCount变量判断用户是否在一个定时器时间内按了数次键
            // 当且仅当eventCount为0，即用户在一个定时器时间内按的第一个有效键才会起作用
            if(keyArray.includes(event.key)&&!eventCount){
                // 当蛇的身体长度>=2时，蛇无法掉头
                if(snakes.length>=2){
                    // 判断用户当前按下的按键与蛇当前的前进方向是否相反
                    // 如果不相反再赋值
                    if(!reverseKeyObject[direction].includes(event.key)){
                        direction=event.key;
                        eventCount++;
                    }
                }else{
                    direction=event.key;
                    eventCount++;
                }
            }
        }
    );


    setTimeout(
        function move(){
            eventCount=0;  // 每次定时器函数执行再将eventCount置0
            // 调用eatFood函数，传入蛇头
            // 每次移动都要判断是否吃到食物
            let head=snakes[0];
            getFood=isEatFood(head);  // 判断蛇是否吃到食物
            // 更新蛇身，判断蛇身是否更新成功
            // 函数返回了false，则说明游戏结束
            if(!updateSnake(head)){
                alert(`游戏结束\n您的得分为:${score}分`);
                if(confirm('要重新开始吗?')){
                    location.reload();
                }
                return;
            }
            setTimeout(move,200-level*10); // 递归进行蛇的移动
        },
        200
    );
}