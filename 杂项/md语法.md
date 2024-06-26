# 												md语法规范

## 一、区块标记

### 第一方面：概念

+ *是指内容独占一块，需前后换行，不和其他标记共处一行的标记。* 

---

### 第二方面：所含元素
#### ①标题
+ *代表了文章中主题的层次。*

+ *语法为（n级标题的数量）个#+‘空格’。* 

---


#### ②段落
+ _一段连续的文字，可包含‘*’、空格、换行、tab等字符。两个段落之间使用空行分隔。在不写任何语法时默认为段落。_

---

  #### ③列表

##### Ⅰ列表概念与详解

+ 概念：一组相关信息的集合。列表分为**有序列表**和**无序列表** 
+ 列表与后续内容之间需要一个空行隔开，即：列表是一个段落
+ 列表允许多层次嵌套

+ 可以在项目中包含段落，只需将段落前添加一个 tab 或 4 个空格

##### Ⅱ无序列表

+ 语法为（‘+’||‘-’）+‘空格’
+ 左边的符号为无序符号

##### Ⅲ有序列表

+ 语法为（数字1）+‘.’+‘空格’
+ 左边的符号是有序的

##### Ⅳ嵌套列表

+ 在第一层列表的基础上按‘Tab’键即可
+ 代码示例如下：

```
+ 1
+ 2
  1. 3
  1. 4
  1. 5
```

---

#### ④分割线

+ 即水平的，用于区分的一条直线
+ 使用三个连续的‘*’或‘-’可以得到

---

#### ⑤引用

+ 语法为‘>’+‘空格’
+ 可以在每行之前加 > ，也可以在段落之前加 1 个 >
+ 引用内部可以使用其他 Markdown 标记
+ 引用内部可以添加新的引用，只需再加一个大于号

---



## 二、行内标记

### 所含元素

#### ①文字加粗

+ 使用前后各 2 个 ( _ 或 \* ) 包含的文字是 **加粗** 文字。

---


#### ②文字倾斜

+ 使用前后各 1 个 ( _ 或 \* ) 包含的文字是 *斜体* 文字。

---


#### ③删除线

+ 使用前后各 2 个 ~ 包含的文字是 ~~删除~~ 文字。

---


#### ④粗斜体

+ 使用前后各 3 个 ( _ 或 \* ) 包含的文字是 ***粗斜体*** 文字

---



## 三、图片

### 所含元素

#### ①插入图片

+ 语法：

~~~
![图片名称](图片路径)
~~~

---




## 四、链接

### 所含元素

#### ①行内链接

+ 语法：

~~~
[超链接名称](超链接地址)
~~~

---

#### ②引用链接

+ 语法：

~~~
[链接名称][链接id]
[链接id]:链接 url "链接ttle"
~~~

+ **注意事项：链接 id 可使用字母、数字、空格，但不区分大小写。** 
---

#### ③自动链接

+ 语法：

~~~
<链接地址>
~~~

---

## 五、脚注

### 第一方面：概念与注意事项

#### ①概念

+ 为名词提供注释，注释将显示在文章末尾。

---

#### ②注意事项

+ 脚注 id 必须唯一。
+ 无论脚注 id 如何起名，显示时一律标为数字，并且按出现顺序排列。

---

### 第二方面：所含元素

#### ①脚注

+ 语法：

~~~
待注释文字[^脚注 id]
[^脚注 id]:注释内容
~~~

---

## 六、双标记

### 一：所含元素

#### ①行内代码块

+ 语法：使用两个 ` 将代码包含起来。


~~~
示例：
在 OC 中输出 Hello May : `MSLog(@"Hello May");`
~~~

运行结果：

```bash
在 OC 中输出 Hello May : `MSLog(@"Hello May");`
```

---

### 二：注意事项

1. **在代码区块内部，", <>,& 将会自动转换为转义字符**
2. **在代码区块内部，Markdown 标记将保持原样，即：星号()就是星号()，不被解释为特殊标记，这样就可以不能继续使用 Markdown 语法了****

---

