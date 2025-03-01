1. `c:/ProgramData/Microsoft/Windows/Start Menu/Programs`是Windows的起始固定菜单路径

+ Windows端口
  + `netstat -ano`查看当前的端口占用情况
  + `netstat -aon|findstr "端口号"`查看指定端口的占用情况，它会在右侧显示进程号
  + `tasklist|findstr "进程号"`查看哪个B应用程序在占用该端口
  + `taskkill /T /F /PID 进程号`来把该进程干掉
+ 清理C盘
  + `win+R`->`%temp%`显示缓存文件夹，里面的文件可以随便删
  + `win+R`->`cmd`->以管理员程序运行->`powercfg -h off`关毕休眠文件，如果想打开再按原操作输入`powercfg -h on`
  + `win+i`->`存储`->`临时文件`->全删掉
  + 微信->设置->文件管理->修改存储路径为其它盘并删掉原数据