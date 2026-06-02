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
1. 先按Ctrl+k，再按v在侧边栏打开markdown preview enhanced插件的对md文件预览 
2. Ctrl+Shift+v打开一个新页，预览markdown preview enhanced插件的对md文件
3. markdown preview enhanced插件的`>`用来合并表格的列、`^`用来合并表格的行,**想要使用该功能需要开启`enableExtendedTableSyntax`配置项**，具体样例为
   + ~~~markdown
        |属性|属性|属性|
        |---|---|---|
        |具体值|>|具体值|
        |具体值|具体值|具体值|
        |^|具体值|具体值|
     ~~~
4. 该插件默认在打开md文件时会随之打开对应的文件预览，使用`disableAutoPreviewForFilePatterns`配置项来关闭掉它，该配置对应一个数组，数组的每个元素所匹配的文件(数组的每个元素需要是路径的匹配模式，且**需要直接精确到文件名，不要写路径**，支持使用`*`作为通配符)都不会触发自动预览功能，如果想彻底禁用，数组元素设置为`*.md`即可

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



## 设置汇总

+ 自定义的全局配置文件在`C:\Users\19285\AppData\Roaming\Code\User\settings.json`
+ 项目的单体配置文件在根目录下的`.vscode\settings.json`文件下

~~~json
{
    "files.autoSave": "afterDelay", //设置在不编辑文本指定时间后自动保存
    "files.autoSaveDelay": 1000,  //设置不编辑文本的对应等待时间(单位:ms)
    "editor.cursorBlinking": "smooth",  //光标移动时更平滑
    "editor.cursorSmoothCaretAnimation": "on",  //设置平滑插入动画
    "editor.minimap.showSlider": "always",  //总是显示迷你地图滑块
    "editor.minimap.scale": 2,  //编辑小地图比例设置为2
    "emmet.variables": {"lang": "zh-CN","charset": "UTF-8"}, //设置HTML5自动生成时lang属性为简体中文，解码字符集默认为UTF-8
    "outline.collapseItems": "alwaysCollapse",  //控制大纲是折叠还是打开
    "path-intellisense.absolutePathToWorkspace": true,  //设置绝对路径开始为磁盘根目录
    "path-intellisense.autoTriggerNextSuggestion": true,  //设置选择建议后自动弹出下一条建议
    "editor.stickyScroll.enabled": false,  // 在滚动时于编辑器上方显示目前的代码在哪个作用域内
    "editor.mouseWheelZoom": true,  // 设置按住ctrl再滚动鼠标滑轮缩放屏幕
    "workbench.tree.indent": 12, // 设置主侧边栏显示的文件夹不同层级相隔宽度为12单位
    "explorer.compactFolders": false,  // 是否在文件夹仅包含一个文件时不把它显示出来，而是使用java的包（像a.b.c）的形式展现
    "editor.tabSize": 4,  // 设置一个Tab为多少个空格
    "editor.insertSpaces": true,  // 是否在按Tab键时插入指定数量的空格
    "editor.detectIndentation": true,  // 设置打开文件时是否按照文件原有的缩进风格显示
    "vue.format.script.initialIndent": true,  // 设置vue文件的script标签的初始缩进与VScode保持一致
    "vue.format.style.initialIndent": true,  // 设置vue文件的style标签的初始缩进与VScode保持一致
    "vue.format.template.initialIndent": true,  // 设置vue文件的template标签的初始缩进与VScode保持一致
    "explorer.autoReveal":false,  // 关闭 点击一个文件时，侧边栏也会导航到该文件 的功能
    "editor.defaultFormatter": "esbenp.prettier-vscode",  // 全局设置文件的默认格式化器
    "prettier.tabWidth": 4,  // 设置prettier格式化时缩进的空格数
    "prettier.useTabs": false,  // 设置prettier格式化时不使用Tab而使用空格代替
    "workbench.tree.enableStickyScroll": false,  // 设置是否浏览左侧文件树时，父目录悬浮粘滞在上方
    "editor.unicodeHighlight.ambiguousCharacters":false,  // 设置是否高亮显示（黄色）容易与ASCii码混淆的字符（比如中文逗号）
    "editor.unicodeHighlight.invisibleCharacters":false,  // 设置是否高亮显示（黄色）仅保留空格或完全没有宽度的字符（比如中文逗号）
    "editor.suggest.showSnippets": true,  // 编辑代码时展示代码提示
    "editor.snippetSuggestions": "top",  // 代码片段在展示提示时显示的位置
    // 让VScode按照指定文件格式解析某一后缀的文件，这里是让VSCode以解析JSON的语法解析后缀是.code-snippets的文件
    "files.associations": {
        "*.code-snippets":"json"
    },  

    // 配置VSCode终端输出改用UTF-8
    "terminal.integrated.profiles.windows": {
        "Command Prompt": {
            "path": "C:\\Windows\\System32\\cmd.exe",  // 作用对象路径
            "args": ["-NoExit", "/K", "chcp 65001"]  // 可选参数
        },
        "PowerShell": {
            "source": "PowerShell",  // 作用对象
            "args": ["-NoExit", "/C", "chcp 65001"],  // 可选参数
        },
    },
    "terminal.integrated.defaultProfile.windows": "PowerShell",  // 设置默认终端为PowerShell


    /* npm 插件配置 */
    "npm-intellisense.importES6": true,
    "npm-intellisense.importQuotes": "'",
    "npm-intellisense.importLinebreak": ";\r\n",
    "npm-intellisense.scanDevDependencies": true,
    "npm-intellisense.importDeclarationType": "const",
    "npm-intellisense.showBuildInLibs": true,
    "npm-intellisense.recursivePackageJsonLookup": true,
    "workbench.iconTheme": "vscode-icons",
    "security.workspace.trust.untrustedFiles": "open",
    /* Path Intellisense插件配置 */
    "typescript.suggest.paths": true,  //启用插件Path Intellisense
    "javascript.suggest.paths": true,
    "git.confirmSync": false,
    "[javascriptreact]": {

    }, //启用插件Path Intellisense

}
~~~