package designPatternSample;

public class SingletonDesignPattern2 {
    // 下面是懒汉式的声明方式

    // 在类中创建实例对象，并用static修饰
    private static volatile SingletonDesignPattern2 singletonDesignPattern1=null;

    // 声明私有构造器
    private SingletonDesignPattern2(){

    }

    // 使用类的静态方法来向外界提供该实例，因为单例模式保证仅有一个实例对象，也就是外界无法通过new的方式得到实例对象，而不得到实例对象无法调用类中的一般方法
    // 因此只能将方法用static修饰，直接使用类名来调用该方法，这样便可以确保单例了
    public static SingletonDesignPattern2 getSingletonDesignPattern1(){
        if(singletonDesignPattern1!=null){
            return singletonDesignPattern1;
        }
        synchronized(singletonDesignPattern1){
            // 懒汉式声明方式在方法被调用时才创建对象，比较节约内存
            if(singletonDesignPattern1==null){
                singletonDesignPattern1=new SingletonDesignPattern2();
            }
            return singletonDesignPattern1;
        }
    }
}
