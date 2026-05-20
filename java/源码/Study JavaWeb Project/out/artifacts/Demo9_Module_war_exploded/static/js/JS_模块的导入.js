import a from "/Demo9/static/js/JS_模块的导出.js"
import * as test from "/Demo9/static/js/JS_模块的导出.js"
import {default as de} from "/Demo9/static/js/JS_模块的导出.js"
console.log(a);  /*  该变量是可以被访问的  */
// console.log(d);  /*  该变量没有被导出，因此会报错，之所以报错而不输出undefined是因为该变量不是对象中的属性，因此在作用域链找不到会报错  */
console.log(de)  /*  需要使用给default起的别名来进行变量的使用  */
console.log(test.b);  // 由于d没有被export修饰，因为输出对象中的属性，找不到会返回undefined而不报错,故输出undefined

let x=1;
console.log(x);