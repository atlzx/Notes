# C#基础笔记

## 一、C#概述

C#是由微软开发的面向对象的编程语言，它是.NET框架的一部分，具有跨平台、运行效率高等特性。

---

## 二、HelloWorld

C#的HelloWorld需要具备如下条件:
  + 创建一个`.cs`为后缀的文件
  + 使用`namespace`关键字声明命名空间
  + 在命名空间中编写一个(或多个)`class`(类)
  + 在类中编写`Main`方法
  + 使用`Console.WriteLine()`方法输出

~~~C#
    namespace Test{
        class HelloWorld{
            static void Main(String[] args){
                Console.WriteLine("Hello World");
                Console.ReadKey();  // 如果使用VS，需要加上这段话以避免VS快速运行代码而看不到结果。但是写了以后需要手动关闭程序
            }
        }
    }
~~~

---

## 三、变量与数据类型

---

## 四、运算符

---

## 五、流程控制

---

## 六、异常处理

---

## 七、面向对象

---

## 八、泛型

---

## 九、多线程

---

## 十、反射

---

## 十一、文件与IO

