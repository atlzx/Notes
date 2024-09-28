# electron

+ [官网](https://www.electronjs.org/zh/docs/latest/)

## 一、安装

<a id="single-install"></a>

### （一）单独安装

~~~bash
    npm install electron --save-dev
~~~

+ 在根目录创建一个main.js文件

<a id="check-block"></a>

+ 在`package.json`检查是否存在如下代码，没有的话加上:

~~~json
{
    // 这些配置在vite项目初始化时被指定
    "name": "electrondemo",
    "private": true,
    "version": "0.0.0",
    "type": "module",
    // 这里要指定我们的electron程序启动所需要的启动的js文件，该文件是electron程序的启动文件，也是打包时必须的文件
    "main": "main.js",
    // 这个必须要有，没有的话需要加上
    "author": "lzx",
    "description": "demo",
    "scripts": {
        // 这里是vite自己加的
        "dev": "vite",
        "build": "vite build",
        "lint": "eslint .",
        "preview": "vite preview",
        // 需要添加的是这行
        "start": "electron .",
    },
    "dependencies": {...},
    "devDependencies": {...}
}

~~~

+ 这样我们的electron程序就安装好了，后面的启动请参考[这里](#HelloWorld)

---

### （二）React+Electron

+ 这里使用React和Electron结合一下，首先创建React项目
  + 使用Vite创建:`npm create vite@latest`，跟着提示走就行
+ 刚初始化完成的vite的React项目直接就能跑，不过依旧需要导入electron的依赖:
+ 之后的配置见上面的[（一）单独安装](#single-install)

---

## 二、使用

<a id="HelloWorld"></a>

### （一）HelloWorld

+ 在根目录创建一个`main.js`文件(当然也可以起别的名字，但必须是js文件)
+ 在里面写这些个东西:
~~~js
    // ESM的导入方式
    import {app,BrowserWindow} from 'electron';
    // COMMONJS的导入方式
    // const { app, BrowserWindow } = require('electron')

    // 该函数用于创建窗口
    const createWindow = () => {
        // 配置窗口的基本配置
        const win = new BrowserWindow(
            {
                width: 800,
                height: 600
            }
        );
        // 指定窗口最开始加载的文件，需要是html文件
        win.loadFile('index.html');
    };
    // 当app就绪后调用createWindow（即上面的函数）来启动一个窗口
    app.whenReady().then(
        () => {
            createWindow()
        }
    );
~~~
+ 我们在js文件内写了加载的html文件，但是此时项目内还没有该文件（如果是vite项目的话会有，但没办法使，因为需要build）
+ 首先，如果没有这个文件，那么就手动在根目录建一个，然后写如下代码:

~~~html
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>demo</title>
    </head>
    <body>
        <div>Hello World</div>
    </body>
</html>
~~~

+ 接下来检查`package.json`文件内的代码是否存在[安装](#check-block)时要求检查的该文件内的代码，如果不存在就加上
  + 如果`main.js`这个文件起了别的名字，那么json文件内的main键对应的文件名也要同步更改
+ 最后执行`npm run start`，就会看到Hello World





---

## 三、打包、发布与更新

### （一）打包

#### ①electron-forge

+ [官网](https://www.electronforge.io/)
+ electron-forge是electron官方推荐的打包依赖:
  + electron并不会自带这东西，需要手动下载
~~~bash
    npm install --save-dev @electron-forge/cli
    npx electron-forge import
~~~
+ 执行完毕后，会发现`package.json`文件中添加了一些东西:

~~~js
  //...
  "scripts": {
    "start": "electron-forge start",
    "package": "electron-forge package",
    "make": "electron-forge make"
  },
  //...
~~~

+ 接下来我们需要执行`npm run make`就好了
  + 然而事情并没有这么简单，vite默认的是ESM的格式，但是`electron-forge`在打包时会生成一个`forge.config.js`文件，它是使用COMMONJS规范书写的，因此会导致冲突
  + 为了解决这一问题，只需要把它的代码内容改成ESM格式就好了
  + 一般来说，改完后的结果（最基本的文件格式）如下所示:

    ~~~js

        import {FusesPlugin} from '@electron-forge/plugin-fuses';
        import { FuseV1Options,FuseVersion } from '@electron/fuses';
        export default{
            packagerConfig: {
                asar: true,
            },
            rebuildConfig: {},
            makers: [
                {
                name: '@electron-forge/maker-squirrel',
                config: {},
                },
                {
                name: '@electron-forge/maker-zip',
                platforms: ['darwin'],
                },
                {
                name: '@electron-forge/maker-deb',
                config: {},
                },
                {
                name: '@electron-forge/maker-rpm',
                config: {},
                },
            ],
            plugins: [
                {
                name: '@electron-forge/plugin-auto-unpack-natives',
                config: {},
                },
                // Fuses are used to enable/disable various Electron functionality
                // at package time, before code signing the application
                new FusesPlugin({
                version: FuseVersion.V1,
                [FuseV1Options.RunAsNode]: false,
                [FuseV1Options.EnableCookieEncryption]: true,
                [FuseV1Options.EnableNodeOptionsEnvironmentVariable]: false,
                [FuseV1Options.EnableNodeCliInspectArguments]: false,
                [FuseV1Options.EnableEmbeddedAsarIntegrityValidation]: true,
                [FuseV1Options.OnlyLoadAppFromAsar]: true,
                }),
            ],
        };

    ~~~

  + 解决完该问题后，再次执行`npm run make`即可
+ 使用`electron-forge`打包的项目，默认会生成一个`out`文件夹
  + 在其中的`electrondemo-win32-x64`中存放有可以直接运行的项目，点击exe文件就能跑
  + 另外，在`make\squirrel.windows\x64\electrondemo-0.0.0 Setup.exe`中可以找到一个`xxx Setup.exe`程序，该程序即为项目的安装程序，但是该安装程序无法自定义，也就是用户无法决定它被安装在哪，也无法决定它是否开机自启等，而且点了就会跑

---

#### ②electron-builder

+ [官网](https://www.electron.build)
+ electron-builder可以实现用户自定义安装流程
+ 安装:

~~~bash
    # 官方推荐用yarn
    yarn add electron-builder --dev

    npm i electron-builder --save-dev
~~~

+ 接下来在配置文件内写这些配置:

~~~json
    {
        "name": "electrondemo",
        "private": true,
        "version": "0.0.0",
        "type": "module",
        "main": "main.js",
        "author": "lzx",
        "description": "demo",
        "scripts": {
            ...
            "dist":"electron-builder"
        },
        "dependencies": {
            ...
        },
        "devDependencies": {
            ...
            // electron-builder依赖必须位于devDependencies中，而不能位于dependencies内，否则打包时会报错
            "electron-builder": "^25.0.5",
            ...
        },
        // 配置electron-builder打包的配置
        "build":{
            "appId":"demo",  // 指定本项目的唯一id
            "productName":"demo",  // 项目名，就是exe文件的名字
            // files表示指定要打包的文件，这里一般需要指定dist内的前端打包文件和main.js入口文件
            "files": [
            "dist/**/*","main.js"
            ],
            "win": {
                // windows系统的打nsis格式的安装包
                "target": "nsis"
            },
            // 以下是nsis的配置，可以酌情选择使用
            "nsis": {
                "oneClick": false, // 是否一键安装，如果为 false，则显示安装向导  
                "allowElevation": true, // 是否允许请求提升（以管理员身份运行）  
                "allowToChangeInstallationDirectory": true, // 是否允许用户更改安装目录  
                "createDesktopShortcut": true, // 是否在桌面上创建快捷方式  
                "createStartMenuShortcut": true, // 是否在开始菜单中创建快捷方式  
                "shortcutName": "YourAppName", // 快捷方式的名称  
                "uninstallDisplayName": "Your App", // 卸载时显示的名称  
                "license": "path/to/license.txt", // 许可证文件的路径  
                "installerIcon": "path/to/installer-icon.ico", // 安装程序图标的路径  
                "uninstallerIcon": "path/to/uninstaller-icon.ico", // 卸载程序图标的路径  
                "installerHeaderIcon": "path/to/header-icon.ico", // 安装向导头部的图标路径  
                "installerSidebarIcon": "path/to/sidebar-icon.bmp", // 安装向导侧边栏的图标路径（必须是 BMP 格式）  
                "runAfterFinish": true, // 安装完成后是否运行应用  
                "perMachine": true, // 是否为所有用户安装（而非仅当前用户）  
                "script": "path/to/custom-nsis-script.nsh", // 自定义 NSIS 脚本的路径  
                "compression": "lzma", // 压缩方式，可选值包括 'none', 'zip', 'lzma' 等  
                "artifactName": "${productName}-${version}-Setup.${ext}", // 自定义输出文件的名称  
            }
        }
    }
~~~

+ 最后直接`npm run dist`，electron-builder会打包到dist文件夹内
+ 它的安装程序也是叫`xxx Setup.exe`，不过**支持用户自定义安装**

---


## 四、问题汇总

|分类|子分类|报错详情|报错原因|解决方案|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|
|**Electron**|>|通用|请注意，electron默认使用的是CommonJS的格式进行代码的编写，而并非Module的模式进行编写，因此会导致其官网给出的`main.js`示例以及打包时的`forge.config.js`代码格式使用的是require的方式而不是import ...from ...的方式，且暴露对象时也是用的是module.exports而不是export default的方式|把这些代码使用Module代码风格替代|无|
|^|^|出现路径问题导致有些静态资源加载不出来|如果直接使用绝对路径，也就是`/`开头，electron程序会直接去此盘根目录寻找，但是前端项目在打包时，其`/`一般都表示其项目的根目录，导致冲突|对于vite项目，解决该问题的方案是在`vite.config.js`中指定`base`属性，配置项详见[Vue笔记](./Vue.md)|无|
|^|启动时报错|`electron Unable to find Electron app at xxx`|未在`package.json`文件中配置main项，导致electron找不到启动的js文件|补上|无|
|^|项目打包|`Authors is required`|`package.json`文件中没有配置Author|配置一下|无|
|**electron-builder**|项目打包|`Description is required`|`package.json`文件中没有配置description|配置一下|无|
|^|^|`Error: Application entry file "main.js" in the "xxx" does not exist. Seems like a wrong configuration.`|`build.files`文件没有配置正确，一般情况下是配置少了重要打包项|把文件加到`build.files`中去|无|
|^|^|`cannot execute  cause=exit status 2 out=....`|貌似是因为普通命令行权限不够导致执行失败|使用管理员运行powerShell或cmd执行构建命令|无|



