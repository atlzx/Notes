## idea的tomcat项目问题

1. 在`WEB-INF`的`web.xml`文件中添加如下语句，可以**设置`idea`的`tomcat`服务默认启动的`jsp`文件**:
~~~xml
    <welcome-file-list>
        <welcome-file>想启动的文件名</welcome-file>
    </welcome-file-list>
~~~
2. 修改`jsp`文件的父文件名时，需要在&nbsp;`点击模块目录->右键->模块设置->facet`中找到`Web资源目录`，修改其路径，使其路径与修改文件名后的路径相同，否则在原`jsp`文件右键不会弹出`运行xxx.jsp`和`调试xxx.jsp`，而且此时启动项目会报错:`404 Not Found`
3. 需要的`jar`包(如`JDBC`需要的`jar`包)需要放置在`WEB-INF`的`lib`文件夹下，并点击该`jar`包，右键点击`添加为库`并设置才能使用
4. 添加本地`tomcat`服务:
   1. 点击老`UI`界面的运行小按钮的左侧
   2. 选择`编辑配置`
   3. 点击左上角小加号，选择`tomcat(本地)`，
      + 在`服务`一侧的`应用程序服务器`的`配置`按钮选项中添加本地的`tomcat`路径
      + 必须在`部署`侧点击加号，添加一个部署，如果不知道怎么做`idea`会报错，直接选择让它自己修复也能配置完成
5. 在`jsp`文件配置设置里可以选择默认打开的浏览器
6. 如果`tomcat`控制台输出乱码，在`tomcat->conf->logging.propertites`内修改`xxxx.xxxx.xxxx.....encoding`属性的值为`GBK`，如果不行改为`UTF-8`
7. `idea`出现无法识别`xxxx()`等方法:`打开模块设置->对应模块->依赖->点击+号->选择添加库->将tomcat导入`
8. 如果`idea`在更改`css`样式但网页依旧不变，可能是因为浏览器的自动缓存机制
   + 浏览器会自动缓存静态文件以加快访问速度，但是这导致了他不会再从服务器端接收静态文档，此时需要`ctrl+F5`完全刷新让浏览器完全删除本地临时文件缓存，从服务器重新接受文档
9. `java.lang.IllegalStateException: 提交响应后无法调用sendRedirect（）`问题:调用`session.sendRedirect（）`方法后，**程序不会终止而是会继续执行**，当多次执行到该方法时，就会出现异常
10. `Operation not allowed after ResultSet closed`问题:使用同一个`Statement`进行多次`excuteQuery`或`excuteUpdate`时，执行下一次`exceute`时会将上一次`Statement`对象方法所返回的`ResultSet`方法关闭，此时若再次调用该`ResultSet`对象，便会报错:
~~~java
    Stament sta=con.createStatement();
    ResultSet rs=sta.executeQuery("select * from xxx");
    sta.executeUpdate("delete * from xxx where xxxx");
    rs.getString("data");  //报错，因为上次的excuteQuery方法产生的ResultSet对象已经被关闭了，原因是sta在之后执行了excuteUpdate方法
    // 应保证在excuteUpdate方法执行前完成需要rs执行的代码，或者创建多个Statement对象以排除互斥
~~~
11. 如果想把一些配置文件**编译后**与源代码放在一起，但是又不希望源代码与它们在一个目录下，可以新建一个`resources`目录，将配置文件放入该目录，`右击该目录->将目录表记为->资源根目录`
12. 在`tomcat`的`conf`目录里的`server.xml`中，可以修改其端口号、查看所用`IP`协议等


## eclipse的tomcat项目问题


