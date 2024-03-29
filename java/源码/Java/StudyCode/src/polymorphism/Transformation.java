package polymorphism;

public class Transformation {
    public static void main(String[] args) {
        // 以下类来源于  polymorphism.Polymorphism(多态样例) 类中，由于是一个包下的，因此可以直接用
        Animal animal = new Cat();
        if(animal instanceof Cat){
            System.out.println("猫");
        }else if(animal instanceof Dog){
            System.out.println("狗");
        }
        Dog dog=(Dog)animal;  // 此时不使用instanceof判断直接向下转型会出现异常
    }
}

