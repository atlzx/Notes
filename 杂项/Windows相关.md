1. `c:/ProgramData/Microsoft/Windows/Start Menu/Programs`是Windows的起始固定菜单路径

+ Windows端口
  + `netstat -ano`查看当前的端口占用情况
  + `netstat -aon|findstr "端口号"`查看指定端口的占用情况，它会在右侧显示进程号
  + `tasklist|findstr "进程号"`查看哪个B应用程序在占用该端口
  + `taskkill /T /F /PID 进程号`来把该进程干掉