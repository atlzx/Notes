package ThreadSample;

public class ThreadExer1 {
    public static void main(String[] args) {
        A a = new A();
        a.start(); //① 启动线程 ② 调用Thread类的run()

        // 实现Runnable接口的线程创建方式需要new Thread(对象)来创建线程对象，而此处new的是Thread的子类
        // 因此除非其子类的构造方法内调用了super(a)，否则调用的依旧是B内的run方法
        B b = new B(a);
        b.start();

    }
}

//创建线程类A
class A extends Thread {
    @Override
    public void run() {
        System.out.println("线程A的run()...");
    }
}

//创建线程类B
class B extends Thread {
    private A a;

    public B(A a) {//构造器中，直接传入A类对象，传入该对象的目的是为了混淆思路，实际上传入的对象仅作为B类实例对象的属性存在，，并没什么影响
        this.a = a;
    }
    /*
        public B(A a){
//            this.a=a;  这样写什么也不会输出
            super(a);  // 这样写会输出A类run方法内输出的值，因为调用了super方法，即使得Thread对象的target指向了A类的实例对象，因此调用target.run时调用的是A类中的run方法
        }
    */

    @Override
    public void run() {
        System.out.println("线程B的run()...");
//        a.run();
    }
}