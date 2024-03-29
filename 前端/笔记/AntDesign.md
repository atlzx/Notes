
# 使用

+ `npm i antd`安装`Ant Design`核心库
+ `npm i @ant-design/icons`安装`Ant Design`图标库

# 表单

## 一、按钮

+ 按钮使用`Button`进行导入

|属性|说明|可选值/参数|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|:---|
|`type`|设置按钮类型|`primary`:主按钮，主按钮在**同一个操作区域内仅允许出现一次**<br>`default`:默认按钮<br>`dashed`:边框是虚线的按钮<br>`link`:链接按钮<br>`text`:文本按钮|`default`|`string`|无|
|`classNames`|自定义按钮的`css`样式|>|`Record<SemanticDOM, string>`|-|无|
|`onClick`|点击按钮时的回调|函数接收一个`event`对象作为参数|`(event: MouseEvent) => void`|-|无|
|`shape`|设置按钮形状|`default`、`circle`、`round`|`default`|`string`|无|
|`size`|设置按钮大小|`large`、`middle`、`small`|`middle`|`string`|无|
|`icon`|设置按钮图标|`React`组件|`React`组件|使用方式:`icon:{<IconName />}`|
|`htmlType`|原生的`html`的`type`属性|`button`、`reset`、`submit`|`string`|`submit`|无|
|`loading`|按钮载入状态|`true`或`false`|`boolean`|`false`|无|
|`disabled`|设置按钮失效状态|`true`或`false`|`boolean`|`false`|无|
|`href`|点击跳转的地址，指定此属性后其效果会与`a`标签一致|`url`|`string`|-|无|
|`target`|打开链接的方式，`href`存在时生效|`_blank`、`_self`等|`string`|-|无|

---

## 二、表格

|属性|说明|可选值|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|:---|
|`columns`|

+ 简单示例

~~~jsx

    import { useState } from 'react';
    import {Table} from 'antd'

    const TableDemo=()=>{
        // 设置表格的列，包括列名、列的对齐方式、列的key值
        const columns=[
            {
                dataIndex:'bookID',
                key:'bookID',
                title:'书籍ID',
                align:'center'
            },
            {
                dataIndex:'bookName',
                key:'bookName',
                title:'书籍名称',
                align:'center'
            },
            {
                dataIndex:'bookAuthor',
                key:'bookAuthor',
                title:'书籍作者',
                align:'center'
            },
            {
                dataIndex:'bookCount',
                key:'bookCount',
                title:'书籍数量',
                align:'center'
            },
            {
                dataIndex:'bookDesc',
                key:'bookDesc',
                title:'书籍描述',
                align:'center'
            },
            {
                dataIndex:'operation',
                key:'operation',
                title:'操作',
                align:'center'
            }
        ];
        // 设置表格的数据，包括key以及每组数据对应的列的各项dataIndex的值
        const data=[
            {
                key:'data1',
                bookID:'book1',
                bookName:'测试1',
                bookAuthor:'用户1',
                bookCount:10,
                bookDesc:'第一本测试的书',
                operation:(<><Button>点我</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button>点我2</Button></>)
            },
            {
                key:'data2',
                bookID:'book2',
                bookName:'测试2',
                bookAuthor:'用户2',
                bookCount:8,
                bookDesc:'第二本测试的书',
                operation:(<><Button>点我</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button>点我2</Button></>)
            },
            {
                key:'data3',
                bookID:'book3',
                bookName:'测试3',
                bookAuthor:'用户3',
                bookCount:12,
                bookDesc:'第三本测试的书',
                operation:(<><Button>点我</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button>点我2</Button></>)
            },
            {
                key:'data4',
                bookID:'book4',
                bookName:'测试4',
                bookAuthor:'用户4',
                bookCount:14,
                bookDesc:'第四本测试的书',
                operation:(<><Button>点我</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button>点我2</Button></>)
            },
        ];

        // 设置多选框
        const [selectState,setSelectState]=useState([]);

        // 每次点击选择按钮时，都对多选框数组进行重新赋值
        const onSelectionChange=(e)=>{
            console.log(e);
            setSelectState(e);
        };
        // 将多选框数组和其对应的事件加入对象，该对象将会作为整合好的对象成为表格的 rowSelection 对应的值
        const selectRow={
            selectedRowKeys:selectState,
            onChange:onSelectionChange
        };

        return (
            <Table dataSource={data} columns={columns} rowSelection={selectRow}></Table>
        );
    };

    export default TableDemo;
~~~

---

## 三、输入框

## 四、选择组件

## 五、上传

+ 上传组件使用`Upload`导入



## 六、表单

# 导航

## 一、导航栏

+ 导航栏使用`Menu`进行导入

+ `Menu`属性

|属性|说明|可选值/参数|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|:---|
|`items`|菜单内容|>|[MenuItemType[]](#MenuItemType)|-|无|
|`className`|自定义设置`css`样式|>|`Record<SemanticDOM, string>`|-|无|
|`onClick`|设置点击`item`时调用的回调函数|`key`:触发事件的对象的`key`值<br>`keyPath`:数组对象<br>`domEvent`:看名字像原生的`js DOM`对象|`function({key, keyPath, domEvent })`|-|无|
|`defaultSelectedKeys`|设置初始选中的菜单项|>|`Array`|-|无|
|`mode`|设置菜单类型|`vertical`:垂直类型<br>`horizontal`:水平类型<br>`inline`:嵌套类型|`string`|`vertical`|无|
|`inlineCollapsed`|菜单类型为`inline`时，菜单是否为**收起状态**|`true`或`false`|`boolean`|-|无|
|详情查看[文档](https://ant-design.antgroup.com/components/menu-cn#menu)|


<a id="MenuItemType"></a>

+ `MenuItemType`属性

|属性|说明|可选值|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|:---|
|`key`|`item`的唯一标识|>|`string`|-|无|
|`label`|菜单项标题|>|`string`|-|无|
|`icon`|设置按钮图标|>|`React`组件|使用方式:`icon:{<IconName />}`|
|`disabled`|设置是否禁用|`true`或`false`|`boolean`|`false`|无|
|详情见[文档](https://ant-design.antgroup.com/components/menu-cn#itemtype)|

---

# 提示

## 一、全局提示

+ [官方文档](https://ant-design.antgroup.com/components/message-cn)
+ 全局提示使用`message`进行导入
+ 现在直接使用钩子函数会报错，因此推荐使用组件的静态方法:

|静态方法|说明|备注|
|:---:|:---:|:---:|
|`message.success(content, [duration], onClose)`|提示成功|无|
|`message.success(config)`|^|无|
|`message.error(content, [duration], onClose)`|提示错误|无|
|`message.error(config)`|^|无|
|`message.info(content, [duration], onClose)`|提示信息|无|
|`message.info(config)`|^|无|
|`message.warning(content, [duration], onClose)`|提示警告|无|
|`message.warning(config)`|^|无|
|`message.loading(content, [duration], onClose)`|提示加载中|无|
|`message.loading(config)`|^|无|
|`message.open(config)`|提示|无|

|参数|说明|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|`content`|显示在提示框中的内容|`string/ReactNode`|-|无|
|`duration`|自动关闭的延时，单位秒，设置值为0时不关闭|`number`|3|无|
|`onClose`|关闭时触发的回调函数|`function`|-|无|

|`config`对象对应参数|说明|类型|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|`content`|显示在提示框中的内容|`ReactNode`|-|无|
|`duration`|自动关闭的延时，单位秒，设置值为0时不关闭|`number`|3|无|
|`onClose`|关闭时触发的回调函数|`function`|-|无|
|`onClick`|点击`message`时触发的回调函数|`function`|-|无|
|`icon`|自定义图标|`ReactNode`|-|无|
|`key`|当前提示的唯一标识|`string/number`|-|无|
|`className`|自定义`css`|`string`|-|无|

---
