package polymorphism;

public class Polymorphism {
    public static void main(String[] args) {
        People people=new People();
        people.adopt(new Dog());
        people.animal.say();
        people.animal.eat();
        people.adopt(new Cat());
        people.animal.say();
        people.animal.eat();
    }
}

class People{
    Animal animal;
    // 在该方法下，可以通过传入不同的Animal的子类对象来设定不同的动物，从而调用这些动物的方法时，实现不同的功能
    public void adopt(Animal animal){
        this.animal=animal;
    }
}

class Animal{
    public void eat(){

    }

    public void say(){

    }
}

class Dog extends Animal{
    @Override
    public void eat(){
        System.out.println("狗吃*");
    }

    @Override
    public void say() {
        System.out.println("狗汪汪叫");
    }
}

class Cat extends Animal{
    @Override
    public void eat(){
        System.out.println("猫吃耗子");
    }

    @Override
    public void say() {
        System.out.println("猫喵喵叫");
    }
}
