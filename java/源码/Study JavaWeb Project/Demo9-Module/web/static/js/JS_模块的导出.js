export let a=1;  /*  对变量、函数、类使用export关键词修饰可以让其所在的文件被导入时，该对象被访问到。这种方式为分别导出  */
let b='aaa';
let c='bbb';
let d=11;
export class Person{
    constructor(name,age){
        this.name=name;
        this.age=age;
    }
    getName(){
        return this.getName;
    }
    getAge(){
        return this.age;
    }
    setAge(age){
        this.age=age;
    }
    setName(name){
        this.name=name;
    }
}

export function hello(){
    console.log('Hello World');
}



/*  export {a,Person,hello}; //  在最后使用该语法进行统一导出  */

export default {b,c};  // 可以一个default对应多个属性，导入时会导入一个对象(Object)
