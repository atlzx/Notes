# VSCode使用

## 一、内置快捷键

|快捷键|作用|备注|
|:---:|:---:|:---:|
|`ctrl+/`|单行注释|无|
|`alt+shift+a`|多行注释|无|
|`ctrl+shift+o`|选择大纲|无|
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

---

## 二、代码技巧

1. 在想要折叠的代码头部写`#region`，然后在尾部写`#endregion`，此代码块便可以全部折叠

---

## 三、插件

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

---

## 四、自定义配置

### （一）配置项

+ 自定义的全局配置文件在`C:\Users\19285\AppData\Roaming\Code\User\settings.json`
+ 项目的单体配置文件在根目录下的`.vscode\settings.json`文件下

---

### （二）自定义代码片段

+ 点击`设置`->`Snippets`->`新建全局代码片段`->输入文件名
+ 在打开的文件中按照格式写，比如:

~~~json
// key为输入提示右侧的提示文字
"快速生成jsx函数式组件":{
    "scope": "javascriptreact",  // scope是作用域，jsx要写javascriptreact
		"prefix": "jsx",  // prefix就表示要写的代码片段简写，此处输入jsx再回车就会生成指定代码片段
    // body用于表示详细的代码片段，使用 $x 来确定鼠标光标的停留位置顺序，写完一个以后按Tab到达下一个片段的停留位置
    // 这是一个数组，每个元素都按\n分割，这样可以实现多行代码片段的实现
		"body": [
			"export const $1 = (props) =>{",
			"    $2",
			"};"
		],
		"description": "快速生成jsx函数式组件"  // 描述信息
}
~~~

---

### （三）预定义变量

+ VSCode中存在一些预定义变量，它们是VSCode内置的环境变量，可以在一些配置文件(settings.json、launch.json、task.json)中被使用
  + 使用:`${workspaceFolder}`以读取其预定义变量
  + 使用`${env:JAVA_HOME}`以读取系统的环境变量
+ 以下是一些常用的预定义变量：

|变量名|描述|备注|
|:---:|:---:|:---:|
|`${workSpaceFolder}`|当前工作区根目录的绝对路径|如果引入了多个工作区，则为当前操作的工作区的绝对路径|
|`${workspaceFolderBasename}`|当前工作区根目录的名称（不包含路径）|如果引入了多个工作区，则为当前操作的工作区的名称|
|`${file}`|当前打开文件的绝对路径|无|
|`${relativeFile}`|当前打开文件相对于工作区根目录的相对路径。|无|
|`${fileBasename}`|当前打开文件的文件名（包含扩展名）|无|
|`${fileBasenameNoExtension}`|当前打开文件的文件名（不包含扩展名）|无|
|`${fileDirname}`|当前打开文件所在目录的绝对路径|无|
|`${fileExtname}`|当前打开文件的扩展名（例如 .js）|无|
|`${cwd}`|当前工作目录（通常是工作区根目录）|无|
|`${lineNumber}`|	当前光标所在的行号|无|
|`${selectedText}`|当前选中的文本|无|
|`${env:VAR_NAME}`|引用环境变量 VAR_NAME 的值（例如 ${env:HOME} 表示用户的主目录）|无|

+ 一些配置可能会用到这些预定义变量:

~~~json
"path-intellisense.mappings": {
    "@": "${workspaceFolder}/src"
}
~~~

---