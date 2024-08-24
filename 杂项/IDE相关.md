# VS Code

## 一、内置快捷键

|快捷键|作用|备注|
|:---:|:---:|:---:|
|`ctrl+/`|单行注释|无|
|`alt+shift+a`|多行注释|无|
|`ctrl_shift+o`|选择大纲|无|
|`ctrl+shift+l`|一次性全部选中与当前选中项相同的字段|无|
|`alt+shift+p`|打开命令符|无|
|`ctrl+f`|搜索与替换|无|
|`alt+shift+f`|格式化整个文档|无|
|`alt+z`|自动换行|无|
|长按`Shift`+键盘方向键|选中光标划过的的文本|无|
|`Ctrl+Shift+k`|删除光标或选中区域的所在行|无|
|`Ctrl+回车`|在不改变当前光标所在的文本格式的情况下另起一行|无|
|`Alt+Shift+↑/↓`|将选中区域或光标所在行向上/下复制粘贴一份|无|
|`Alt+↑/↓`|使相邻两行的代码交换位置|无|
|`Ctrl+[`|向左缩进|无|
|长按`Alt`+点击鼠标|连续选中多个想修改的行的地方|按`Alt+↑/↓`使这些光标整体向上/下移动|
|`lorem`+数字|随机生成指定单词数量的句子|不写数字会随机生成句子|

## 二、代码技巧

1. 在想要折叠的代码头部写`#region`，然后在尾部写`#endregion`，此代码块便可以全部折叠

---

## 三、VSCode插件

### （一）markdown preview enhanced
1. (Ctrl+k)+v在侧边栏打开markdown preview enhanced插件的对md文件预览 
2. Ctrl+Shift+v打开一个新页，预览markdown preview enhanced插件的对md文件
3. markdown preview enhanced插件的`>`用来合并表格的列、`^`用来合并表格的行,具体样例为
   + ~~~markdown
        |属性|属性|属性|
        |---|---|---|
        |具体值|>|具体值|
        |具体值|具体值|具体值|
        |^|具体值|具体值|
     ~~~

---

### （二）Live Server

1. 使用Live Server启动的浏览器，在源代码修改后，网页内容会同步修改
2. Live Server会调用本地的服务器，将网页内容存入服务器后以服务器路径打开网页
3. Live Server必须要在VSCode打开文件夹的前提下且目标网页源代码符合规范才可以使用网页自动刷新功能
4. Live Server可以检查出HTML文件解码乱码的情况，并在使用Live Server打开浏览器时更正这一错误
5. Live Server并不支持使用绝对路径，因此在打开Live Server的前提下打开浏览器并使用绝对路径时打开文件可能会打不开

## 四 自定义配置

+ 自定义的全局配置文件在`C:\Users\19285\AppData\Roaming\Code\User\settings.json`
+ 项目的单体配置文件在根目录下的`settings.json`文件下




# Pycharm

## 一、内置快捷键

1. `Ctrl+y`删除当前行
2. `Ctrl+z`撤销操作
3. `Ctrl+Shift+z`恢复操作
4. `Ctrl+/`注释
5. `Ctrl+p`提示对应函数的参数

# Windows系统

1. `Win+V`打开剪贴板
2. 使用`Win+r`输入`regedit`打开注册表，在`计算机\HKEY_CLASSES_ROOT\Directory`的全部文件夹和`计算机\HKEY_CLASSES_ROOT\*`路径下的与`shell`相关的文件夹内有与鼠标右键菜单栏相关的文件，删除后可不再让其显示

